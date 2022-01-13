package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.megatron.exception.TooManyBloodUnitsException;
import progi.megatron.model.BankWorker;
import progi.megatron.model.BloodSupply;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.BloodSupplyRequestDTO;
import progi.megatron.model.dto.BloodSupplyResponseDTO;
import progi.megatron.repository.BloodSupplyRepository;
import progi.megatron.validation.BloodSupplyValidator;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BloodSupplyService {

    public final List<String> bloodTypes = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");

    private final BloodSupplyRepository bloodSupplyRepository;
    private final BloodSupplyValidator bloodSupplyValidator;
    private final ModelMapper modelMapper;
    private final DonorService donorService;
    private final BankWorkerService bankWorkerService;
    //private final DonationTryService donationTryService;

    @Autowired
    private EmailService emailService;

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository, BloodSupplyValidator bloodSupplyValidator, ModelMapper modelMapper, DonorService donorService, BankWorkerService bankWorkerService) {
        this.bloodSupplyRepository = bloodSupplyRepository;
        this.bloodSupplyValidator = bloodSupplyValidator;
        this.modelMapper = modelMapper;
        this.donorService = donorService;
        this.bankWorkerService = bankWorkerService;
        //this.donationTryService = donationTryService;
    }

    public BloodSupplyResponseDTO getBloodSupplyByBloodType(String bloodType) {
        bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
        BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
        return new BloodSupplyResponseDTO(bloodSupply.getBloodType(), bloodSupply.getNumberOfUnits(), getReview(bloodSupply), bloodSupply.getMaxUnits(), bloodSupply.getMinUnits());
    }

    public int[] manageBloodSupply(String[] bloodTypes, int[] bloodSupplies, boolean increase) {
        int i = 0;
        int[] newStates = new int[8];
        List<String> bloodTypesOverLimit = new LinkedList<>();
        List<String> bloodTypesUnderLimit = new LinkedList<>();
        for(String bloodType : bloodTypes){
            int numberOfUnits = bloodSupplies[i];
            BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
            int oldNumberOfUnits = bloodSupply.getNumberOfUnits();
            if (oldNumberOfUnits < numberOfUnits) {
                throw new TooManyBloodUnitsException("U sustavu nema dovoljno krvi tražene krvne grupe.");
            }
            bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
            List<Donor> donors = donorService.getAllDonorsWithBloodType(bloodType);
            if (increase) {
                if(oldNumberOfUnits <= bloodSupply.getMaxUnits() && (oldNumberOfUnits+1) > bloodSupply.getMaxUnits()){
                    bloodTypesOverLimit.add(bloodType.trim());
                }
                bloodSupplyRepository.manageBloodSupply(bloodType, oldNumberOfUnits + 1);
            } else {
                if(oldNumberOfUnits >= bloodSupply.getMinUnits()  && (oldNumberOfUnits - numberOfUnits) < bloodSupply.getMinUnits()){
                    bloodTypesUnderLimit.add(bloodType.trim());
                }
                bloodSupplyRepository.manageBloodSupply(bloodType, oldNumberOfUnits - numberOfUnits);
            }
            newStates[i] = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType).getNumberOfUnits();
            i++;
        }
        if(bloodTypesOverLimit.size() > 0){
            sendMailTooMuchBlood(bloodTypesOverLimit);
        }
        if(bloodTypesUnderLimit.size() > 0){
            sendMailTooLittleBlood(bloodTypesUnderLimit);
        }
        return newStates;
    }

    private void sendMailTooMuchBlood(List<String> bloodTypesOverLimit) {
        List<BankWorker> bankWorkers = bankWorkerService.getAllBankWorkers();
        Set<String> emailsSent = new HashSet<>();
        for(BankWorker bankWorker : bankWorkers){
            if(emailsSent.contains(bankWorker.getEmail())) continue;
            try {
                emailService.tooMuchBloodEmail(bankWorker.getEmail(), bankWorker.getFirstName(), bloodTypesOverLimit);
                emailsSent.add(bankWorker.getEmail());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMailTooLittleBlood(List<String> bloodTypesUnderLimit) {
        List<BankWorker> bankWorkers = bankWorkerService.getAllBankWorkers();
        Set<Donor> donorsToSend = donorService.getAllDonors()
                .stream()
                //.filter(d -> donationTryService.getWhenIsWaitingPeriodOverForDonor(d.getDonorId().toString()) < 1)
                .filter(d -> d.getBloodType() != null && bloodTypesUnderLimit.contains(d.getBloodType().trim()))
                .collect(Collectors.toSet());
        System.out.println(donorsToSend);
        Set<String> emailsSent = new HashSet<>();

        for(Donor donor : donorsToSend){
            //if(emailsSent.contains(donor.getEmail())) continue;
            try {
                emailService.tooLittleBloodEmail(donor.getEmail(),donor.getFirstName(), true, List.of(donor.getBloodType()));
                //emailsSent.add(donor.getEmail());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        emailsSent.clear();
        for(BankWorker bankWorker : bankWorkers){
            if(emailsSent.contains(bankWorker.getEmail())) continue;
            try {
                System.out.println(bloodTypesUnderLimit);
                emailService.tooLittleBloodEmail(bankWorker.getEmail(), bankWorker.getFirstName(), false, bloodTypesUnderLimit);
                emailsSent.add(bankWorker.getEmail());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public List<BloodSupplyResponseDTO> getBloodSupply() {
        List<BloodSupplyResponseDTO> bloodSupplies = new ArrayList<>();
        for (String bloodyType : bloodTypes) {
            bloodSupplies.add(getBloodSupplyByBloodType(bloodyType));
        }
        return bloodSupplies;
    }

    public BloodSupply[] setMinMax(BloodSupplyRequestDTO bloodSupplyRequestDTO) {
        int i = 0;
        BloodSupply[] supplies = new BloodSupply[bloodSupplyRequestDTO.getBloodTypes().length];
        List<String> bloodTypesUnderLimit = new LinkedList<>();
        List<String> bloodTypesOverLimit = new LinkedList<>();

        for(String bloodType : bloodSupplyRequestDTO.getBloodTypes()){
            bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
            BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
            String oldReview = getReview(bloodSupply);
            bloodSupply.setMaxUnits(bloodSupplyRequestDTO.getMaxUnits()[i]);
            bloodSupply.setMinUnits(bloodSupplyRequestDTO.getMinUnits()[i]);
            bloodSupplyValidator.validateBloodSupply(bloodSupply);
            bloodSupplyRepository.save(bloodSupply);
            String review = getReview(bloodSupply);
            if( !oldReview.equals("TOO LITTLE") && review.equals("TOO LITTLE")){
                bloodTypesUnderLimit.add(bloodType);
            }
            if( !oldReview.equals("TOO MUCH") && review.equals("TOO MUCH")){
                bloodTypesOverLimit.add(bloodType);
            }
            supplies[i] = bloodSupply;
            i++;
        }
        if(bloodTypesOverLimit.size() != 0){
            sendMailTooMuchBlood(bloodTypesOverLimit);
        }
        if(bloodTypesUnderLimit.size() != 0){
            sendMailTooLittleBlood(bloodTypesUnderLimit);
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
