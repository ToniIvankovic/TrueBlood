package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.model.BloodSupply;
import progi.megatron.repository.BloodSupplyRepository;

@Service
public class BloodSupplyService {

    private BloodSupplyRepository bloodSupplyRepository;

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository) {
        this.bloodSupplyRepository = bloodSupplyRepository;
    }

    public BloodSupply getBloodsupplyByBloodType(String bloodType) {
        //TODO validation bloodtype
        return bloodSupplyRepository.getBloodsupplyByBloodType(bloodType);
    }
}
