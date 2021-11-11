package progi.megatron.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonorByBankWorkerDTO;
import progi.megatron.model.dto.DonorByDonorDTO;
import progi.megatron.service.DonorService;

@RestController
@RequestMapping("/api/v1/donor")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @Secured({"ROLE_DONOR"})
    @PostMapping("/registration")
    public ResponseEntity<Donor> createDonorByDonor(@RequestBody DonorByDonorDTO donorByDonorDTO) {
        return ResponseEntity.ok(donorService.createDonorByDonor(donorByDonorDTO));
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/add-donor")
    public ResponseEntity<Donor> createDonorByBankWorker(@RequestBody DonorByBankWorkerDTO donorByBankWorkerDTO) {
        return ResponseEntity.ok(donorService.createDonorByBankWorker(donorByBankWorkerDTO));
    }

}
