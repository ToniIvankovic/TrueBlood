package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonorByBankWorkerDTO;
import progi.megatron.model.dto.DonorByDonorDTO;
import progi.megatron.service.DonorService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/donor")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    // TODO: fix oib not unique response
    @PostMapping("/registration")
    public ResponseEntity<Object> createDonorByDonor(@RequestBody DonorByDonorDTO donorByDonorDTO) {
        try {
            return ResponseEntity.ok(donorService.createDonorByDonor(donorByDonorDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/add-donor")
    public ResponseEntity<Object> createDonorByBankWorker(@RequestBody DonorByBankWorkerDTO donorByBankWorkerDTO) {
        try {
            return ResponseEntity.ok(donorService.createDonorByBankWorker(donorByBankWorkerDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
