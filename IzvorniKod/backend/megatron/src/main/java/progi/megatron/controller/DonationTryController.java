package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.DonationTry;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonationTryDTO1;
import progi.megatron.service.DonationTryService;

@Controller
@RequestMapping("/api/v1/donation-try")
public class DonationTryController {

    private final DonationTryService donationTryService;

    public DonationTryController(DonationTryService donationTryService) {
        this.donationTryService = donationTryService;
    }

    @PostMapping
    public ResponseEntity<Object> createDonationTry(@RequestBody DonationTryDTO1 donationTryDTO1){
        try {
            return ResponseEntity.ok(donationTryService.createDonationTry(donationTryDTO1));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
