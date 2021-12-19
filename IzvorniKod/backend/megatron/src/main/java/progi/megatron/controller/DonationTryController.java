package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.DonationTry;
import progi.megatron.model.dto.DonationTryRequestDTO;
import progi.megatron.service.DonationTryService;

@Controller
@RequestMapping("/api/v1/donation-try")
public class DonationTryController {

    private final DonationTryService donationTryService;

    public DonationTryController(DonationTryService donationTryService) {
        this.donationTryService = donationTryService;
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping
    public ResponseEntity<Object> createDonationTry(@RequestBody DonationTryRequestDTO donationTryRequestDTO){
        try {
            return ResponseEntity.ok(donationTryService.createDonationTry(donationTryRequestDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_DONOR"})
    @GetMapping("/{donorId}")
    public ResponseEntity<Object> getDonationTryHistory(@PathVariable String donorId) {
        try {
            return ResponseEntity.ok(donationTryService.getDonationTryHistory(donorId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_DONOR"})
    @GetMapping("/pdf/{donationId}")
    public ResponseEntity<Object> getSuccessfulDonationPdfCert(@PathVariable String donationId) {
        try {
            DonationTry donationTry = donationTryService.getDonationTryByDonationId(donationId);
            if (donationTry.getRejectReason() == null) {

            }
            return ResponseEntity.ok(donationTry);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
