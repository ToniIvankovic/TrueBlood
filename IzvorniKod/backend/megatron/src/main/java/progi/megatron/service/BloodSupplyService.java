package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import progi.megatron.model.BloodSupply;
import progi.megatron.model.dto.BloodSupplyRequestDTO;
import progi.megatron.model.dto.BloodSupplyResponseDTO;
import progi.megatron.repository.BloodSupplyRepository;
import progi.megatron.validation.BloodSupplyValidator;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodSupplyService {

    private final BloodSupplyRepository bloodSupplyRepository;
    private final BloodSupplyValidator bloodSupplyValidator;
    private final ModelMapper modelMapper;

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository, BloodSupplyValidator bloodSupplyValidator, ModelMapper modelMapper) {
        this.bloodSupplyRepository = bloodSupplyRepository;
        this.bloodSupplyValidator = bloodSupplyValidator;
        this.modelMapper = modelMapper;
    }

    public BloodSupplyResponseDTO getBloodSupplyByBloodType(String bloodType) {
        bloodSupplyValidator.validateBloodType(bloodType);
        BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
        return new BloodSupplyResponseDTO(bloodSupply.getBloodType(), bloodSupply.getNumberOfUnits(), getReview(bloodSupply), bloodSupply.getMaxUnits(), bloodSupply.getMinUnits());
    }

    public String donateBlood(String bloodType) {
        bloodSupplyValidator.validateBloodType(bloodType);
        int oldNumberOfUnits = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType).getNumberOfUnits();
        bloodSupplyRepository.donateBlood(bloodType, oldNumberOfUnits + 1);
        return bloodType;
    }

    public List<BloodSupplyResponseDTO> getBloodSupply() {
        List<String> bloodTypes = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");
        List<BloodSupplyResponseDTO> bloodSupplies = new ArrayList<>();
        for (String bloodyType : bloodTypes) {
            bloodSupplies.add(getBloodSupplyByBloodType(bloodyType));
        }
        return bloodSupplies;
    }

    public BloodSupply setMinMax(BloodSupplyRequestDTO bloodSupplyRequestDTO) {
        bloodSupplyValidator.validateBloodType(bloodSupplyRequestDTO.getBloodType());
        BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodSupplyRequestDTO.getBloodType());
        bloodSupply = modelMapper.map(bloodSupplyRequestDTO, BloodSupply.class);
        bloodSupplyValidator.validateBloodSupply(bloodSupply);
        bloodSupplyRepository.save(bloodSupply);
        return bloodSupply;
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
