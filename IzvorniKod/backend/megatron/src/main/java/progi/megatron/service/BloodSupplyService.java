package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.model.BloodSupply;
import progi.megatron.repository.BloodSupplyRepository;
import progi.megatron.validation.BloodTypeValidator;

@Service
public class BloodSupplyService {

    private BloodSupplyRepository bloodSupplyRepository;
    private final BloodTypeValidator bloodTypeValidator;

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository, BloodTypeValidator bloodTypeValidator) {
        this.bloodSupplyRepository = bloodSupplyRepository;
        this.bloodTypeValidator = bloodTypeValidator;
    }

    public BloodSupply getBloodsupplyByBloodType(String bloodType) {
        bloodTypeValidator.validateBloodType(bloodType);
        return bloodSupplyRepository.getBloodsupplyByBloodType(bloodType);
    }
}
