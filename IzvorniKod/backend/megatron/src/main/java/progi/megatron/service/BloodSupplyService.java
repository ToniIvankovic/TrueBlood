package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.repository.BloodSupplyRepository;

@Service
public class BloodSupplyService {

    private BloodSupplyRepository bloodSupplyRepository;

    public BloodSupplyService(BloodSupplyRepository bloodSupplyRepository) {
        this.bloodSupplyRepository = bloodSupplyRepository;
    }

}
