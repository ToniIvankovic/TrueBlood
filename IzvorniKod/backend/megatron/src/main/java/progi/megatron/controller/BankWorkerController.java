package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.dto.BankWorkerDTO;
import progi.megatron.service.BankWorkerService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/bank-worker")
public class BankWorkerController {

    private final BankWorkerService bankWorkerService;

    public BankWorkerController(BankWorkerService bankWorkerService) {
        this.bankWorkerService = bankWorkerService;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<Object> createBankWorker(@RequestBody BankWorkerDTO bankWorkerDTO) {
        try {
            return ResponseEntity.ok(bankWorkerService.createBankWorker(bankWorkerDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/oib/{oib}")
    public ResponseEntity<Object> getBankWorkerByOib(@PathVariable String oib) {
        try {
            return ResponseEntity.ok(bankWorkerService.getBankWorkerByOib(oib));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @Secured({"ROLE_ADMIN","ROLE_BANK_WORKER"})
    @GetMapping("/id/{bankWorkerId}")
    public ResponseEntity<Object> getBankWorkerByBankWorkerId(@PathVariable String bankWorkerId) {
        try {
            return ResponseEntity.ok(bankWorkerService.getBankWorkerByBankWorkerId(bankWorkerId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
