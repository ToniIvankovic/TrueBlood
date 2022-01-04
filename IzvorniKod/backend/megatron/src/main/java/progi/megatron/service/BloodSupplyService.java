package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import progi.megatron.exception.TooManyBloodUnitsException;
import progi.megatron.model.BloodSupply;
import progi.megatron.model.dto.BloodSupplyRequestDTO;
import progi.megatron.model.dto.BloodSupplyResponseDTO;
import progi.megatron.repository.BloodSupplyRepository;
import progi.megatron.validation.BloodSupplyValidator;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodSupplyService {

    public final List<String> bloodTypes = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");

    private final BloodSupplyRepository bloodSupplyRepository;
    private final BloodSupplyValidator bloodSupplyValidator;
    private final ModelMapper modelMapper;

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository, BloodSupplyValidator bloodSupplyValidator, ModelMapper modelMapper) {
        this.bloodSupplyRepository = bloodSupplyRepository;
        this.bloodSupplyValidator = bloodSupplyValidator;
        this.modelMapper = modelMapper;
    }

    public BloodSupplyResponseDTO getBloodSupplyByBloodType(String bloodType) {
        bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
        BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
        return new BloodSupplyResponseDTO(bloodSupply.getBloodType(), bloodSupply.getNumberOfUnits(), getReview(bloodSupply), bloodSupply.getMaxUnits(), bloodSupply.getMinUnits());
    }

    public int[] manageBloodSupply(String[] bloodTypes, int[] bloodSupplies, boolean increase) {
        int i = 0;
        int[] newStates = new int[8];
        System.out.println(bloodSupplies);
        for(String bloodType : bloodTypes){
            int numberOfUnits = bloodSupplies[i];
            bloodSupplyValidator.validateBloodType(bloodType, this.bloodTypes);
            int oldNumberOfUnits = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType).getNumberOfUnits();
            if (increase) {
                bloodSupplyRepository.manageBloodSupply(bloodType, oldNumberOfUnits + 1);
            } else if (oldNumberOfUnits < numberOfUnits) {
                throw new TooManyBloodUnitsException("Broj jedinica krvi za slanje veći je od dostupnog broja jedinica u banci.");
            } else {
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

    public BloodSupply[] setMinMax(BloodSupplyRequestDTO bloodSupplyRequestDTO) {
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
