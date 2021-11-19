package progi.megatron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import progi.megatron.service.BankWorkerService;

@Controller
@RequestMapping("/api/v1/bank-worker")
public class BankWorkerController {

    private final BankWorkerService bankWorkerService;

    public BankWorkerController(BankWorkerService bankWorkerService) {
        this.bankWorkerService = bankWorkerService;
    }

}
