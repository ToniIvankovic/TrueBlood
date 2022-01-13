package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.megatron.exception.TooManyBloodUnitsException;
import progi.megatron.model.BloodSupply;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.BloodSupplyRequestDTO;
import progi.megatron.model.dto.BloodSupplyResponseDTO;
import progi.megatron.repository.BloodSupplyRepository;
import progi.megatron.validation.BloodSupplyValidator;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodSupplyService {

    public final List<String> bloodTypes = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");

    private final BloodSupplyRepository bloodSupplyRepository;
    private final BloodSupplyValidator bloodSupplyValidator;
    private final ModelMapper modelMapper;
    private final DonorService donorService;
    private final DonationTryService donationTryService;
    private boolean overLimit = false;

    @Autowired
    private EmailService emailService;

    public boolean isOverLimit() {
        return overLimit;
    }

    public void setOverLimit(boolean overLimit) {
        this.overLimit = overLimit;
    }

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository, BloodSupplyValidator bloodSupplyValidator, ModelMapper modelMapper, DonorService donorService, DonationTryService donationTryService) {
        this.bloodSupplyRepository = bloodSupplyRepository;
        this.bloodSupplyValidator = bloodSupplyValidator;
        this.modelMapper = modelMapper;
        this.donorService = donorService;
        this.donationTryService = donationTryService;
    }

    public BloodSupplyResponseDTO getBloodSupplyByBloodType(String bloodType) {
        bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
        BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
        return new BloodSupplyResponseDTO(bloodSupply.getBloodType(), bloodSupply.getNumberOfUnits(), getReview(bloodSupply), bloodSupply.getMaxUnits(), bloodSupply.getMinUnits());
    }

    public int[] manageBloodSupply(String[] bloodTypes, int[] bloodSupplies, boolean increase) throws MessagingException {
        int i = 0;
        int[] newStates = new int[8];
        System.out.println(bloodSupplies);
        for(String bloodType : bloodTypes){
            int numberOfUnits = bloodSupplies[i];
            bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
            List<Donor> donors = donorService.getAllDonorsWithBloodType(bloodType);
            BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
            int oldNumberOfUnits = bloodSupply.getNumberOfUnits();
            if (increase) {
                if(bloodSupply.getMaxUnits() <= (oldNumberOfUnits+1) && !overLimit){
                    for(Donor donor : donors){
                        if(donationTryService.getWhenIsWaitingPeriodOverForDonor(donor.getDonorId().toString()) < 1){
                            emailService.tooMuchBloodEmail(donor.getEmail(),donor.getFirstName());
                        }
                    }
                }
                overLimit = true;
                bloodSupplyRepository.manageBloodSupply(bloodType, oldNumberOfUnits + 1);
            } else if (oldNumberOfUnits < numberOfUnits) {
                throw new TooManyBloodUnitsException("Ne postoji dovoljno jedinica krvi za provedbu željene transakcije.");
            } else {
                overLimit = false;
                if(bloodSupply.getMaxUnits() < oldNumberOfUnits && bloodSupply.getMaxUnits()> (oldNumberOfUnits-numberOfUnits)){
                    for(Donor donor : donors){
                        if(donationTryService.getWhenIsWaitingPeriodOverForDonor(donor.getDonorId().toString()) < 1){
                            emailService.canDonateAgainEmail(donor.getEmail(),donor.getFirstName());
                        }
                    }
                }
                else if(bloodSupply.getMinUnits() < oldNumberOfUnits && bloodSupply.getMinUnits() > (oldNumberOfUnits - numberOfUnits)){
                    for(Donor donor : donors){
                        if(donationTryService.getWhenIsWaitingPeriodOverForDonor(donor.getDonorId().toString()) < 1){
                            emailService.tooLittleBloodEmail(donor.getEmail(),donor.getFirstName());
                        }
                    }
                }
                bloodSupplyRepository.manageBloodSupply(bloodType, oldNumberOfUnits - numberOfUnits);
            }
            newStates[i] = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType).getNumberOfUnits();
            i++;
        }
        return newStates;
    }

    public List<BloodSupplyResponseDTO> getBloodSupply() {
        List<BloodSupplyResponseDTO> bloodSupplies = new ArrayList<>();
        for (String bloodyType : bloodTypes) {
            bloodSupplies.add(getBloodSupplyByBloodType(bloodyType));
        }
        return bloodSupplies;
    }

    public BloodSupply[] setMinMax(BloodSupplyRequestDTO bloodSupplyRequestDTO) throws MessagingException {
        int i = 0;
        BloodSupply[] supplies = new BloodSupply[bloodSupplyRequestDTO.getBloodTypes().length];
        for(String bloodType : bloodSupplyRequestDTO.getBloodTypes()){
            bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
            //BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodSupplyRequestDTO.getBloodType());
            BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
            bloodSupply.setMaxUnits(bloodSupplyRequestDTO.getMaxUnits()[i]);
            bloodSupply.setMinUnits(bloodSupplyRequestDTO.getMinUnits()[i]);
            bloodSupplyValidator.validateBloodSupply(bloodSupply);
            bloodSupplyRepository.save(bloodSupply);
            String review = getReview(bloodSupply);
            if(review.equals("TOO LITTLE")){
                List<Donor> donors = donorService.getAllDonorsWithBloodType(bloodType);
                for(Donor donor : donors){
                    if(donationTryService.getWhenIsWaitingPeriodOverForDonor(donor.getDonorId().toString()) < 1){
                        emailService.tooLittleBloodEmail(donor.getEmail(),donor.getFirstName());
                    }
                }
            }
            if(review.equals("TOO MUCH")){
                List<Donor> donors = donorService.getAllDonorsWithBloodType(bloodType);
                for(Donor donor : donors){
                    if(donationTryService.getWhenIsWaitingPeriodOverForDonor(donor.getDonorId().toString()) < 1){
                        emailService.tooMuchBloodEmail(donor.getEmail(),donor.getFirstName());
                    }
                }
            }
            supplies[i] = bloodSupply;
            i++;
        }
        return supplies;
    }

    public String getReview(BloodSupply bloodSupply) {
        int numberOfUnits = bloodSupply.getNumberOfUnits();
        int maxUnits = bloodSupply.getMaxUnits();
        int minUnits = bloodSupply.getMinUnits();
        if (numberOfUnits < minUnits) {
            return "TOO LITTLE";
        } else if (numberOfUnits > maxUnits) {
            return "TOO MUCH";
        } else return "GOOD";
    }

}
