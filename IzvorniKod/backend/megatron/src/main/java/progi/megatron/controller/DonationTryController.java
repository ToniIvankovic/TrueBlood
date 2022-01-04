package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.dto.DonationTryRequestDTO;
import progi.megatron.service.DonationTryService;
import progi.megatron.util.CurrentUserUtil;
import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/donation-try")
public class DonationTryController {

    private final DonationTryService donationTryService;
    private final CurrentUserUtil currentUserUtil;

    public DonationTryController(DonationTryService donationTryService, CurrentUserUtil currentUserUtil) {
        this.donationTryService = donationTryService;
        this.currentUserUtil = currentUserUtil;
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
    public ResponseEntity<Object> getDonationTryHistory(@PathVariable String donorId, HttpServletRequest request) {
        try {
            if (!currentUserUtil.checkIfCurrentUser(request, donorId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Donor can not fetch other donor's donation history.");
            }
            return ResponseEntity.ok(donationTryService.getDonationTryHistory(donorId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // todo: finish
    // todo: for current user
    @Secured({"ROLE_DONOR"})
    @GetMapping("/pdf/{donationId}")
    public ResponseEntity<Object> getSuccessfulDonationPdfCert(@PathVariable String donationId) {
        try {
            donationTryService.getDonationTryByDonationId(donationId);
            return ResponseEntity.ok("Successfully downloaded PDF certificate.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
