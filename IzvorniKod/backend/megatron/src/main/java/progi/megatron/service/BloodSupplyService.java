package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.model.BloodSupply;
import progi.megatron.model.dto.BloodSupplyDTO;
import progi.megatron.repository.BloodSupplyRepository;
import progi.megatron.validation.BloodTypeValidator;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloodSupplyService {

    private final BloodSupplyRepository bloodSupplyRepository;
    private final BloodTypeValidator bloodTypeValidator;

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository, BloodTypeValidator bloodTypeValidator) {
        this.bloodSupplyRepository = bloodSupplyRepository;
        this.bloodTypeValidator = bloodTypeValidator;
    }

    public BloodSupplyDTO getBloodSupplyByBloodType(String bloodType) {
        bloodTypeValidator.validateBloodType(bloodType);
        BloodSupply bloodSupply = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType);
        return new BloodSupplyDTO(bloodSupply.getBloodType(), bloodSupply.getNumberOfUnits());
    }

    public String donateBlood(String bloodType) {
        bloodTypeValidator.validateBloodType(bloodType);
        int oldNumberOfUnits = bloodSupplyRepository.getBloodSupplyByBloodType(bloodType).getNumberOfUnits();
        bloodSupplyRepository.donateBlood(bloodType, oldNumberOfUnits + 1);
        return bloodType;
    }

    public List<BloodSupplyDTO> getBloodSupply() {
        List<String> bloodTypes = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");
        List<BloodSupplyDTO> bloodSupplies = new ArrayList<>();
        for (String bloodyType : bloodTypes) {
            bloodSupplies.add(getBloodSupplyByBloodType(bloodyType));
        }
        return bloodSupplies;
    }
}
