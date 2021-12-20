package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.dto.DonationTryDTO;
import progi.megatron.service.DonationTryService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/donation-try")
public class DonationTryController {

    private final DonationTryService donationTryService;

    public DonationTryController(DonationTryService donationTryService) {
        this.donationTryService = donationTryService;
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping
    public ResponseEntity<Object> createDonationTry(@RequestBody DonationTryDTO donationTryDTO){
        try {
            boolean donated = donationTryService.createDonationTry(donationTryDTO);
            if (donated) return ResponseEntity.ok("Successfully donated blood.");
            else return ResponseEntity.ok("Could not donate blood.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
