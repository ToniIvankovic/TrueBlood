package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.repository.BankWorkerRepository;

@Service
public class BankWorkerService {

    private final BankWorkerRepository bankWorkerRepository;

    public BankWorkerService(BankWorkerRepository bankWorkerRepository) {
        this.bankWorkerRepository = bankWorkerRepository;
    }

}
