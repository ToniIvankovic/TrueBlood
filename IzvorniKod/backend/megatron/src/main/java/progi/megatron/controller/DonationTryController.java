package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import progi.megatron.exception.WrongDonationTryException;
import progi.megatron.model.DonationTry;
import progi.megatron.model.dto.DonationTryRequestDTO;
import progi.megatron.service.DonationTryService;
import progi.megatron.service.UserService;
import progi.megatron.util.CurrentUserUtil;
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/donation-try")
public class DonationTryController {

    private final DonationTryService donationTryService;
    private final CurrentUserUtil currentUserUtil;
    private final UserService userService;
    private Long before;
    private Long after;

    public DonationTryController(UserService userService, DonationTryService donationTryService, CurrentUserUtil currentUserUtil) {
        this.donationTryService = donationTryService;
        this.currentUserUtil = currentUserUtil;
        this.userService = userService;
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

    @Secured({"ROLE_BANK_WORKER", "ROLE_DONOR"})
    @GetMapping("/{donorId}")
    public ResponseEntity<Object> getDonationTryHistory(@PathVariable String donorId) {
        try {
            String role = currentUserUtil.getCurrentUserRole();
            String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!currentUserId.equals(donorId) && role.equals("DONOR")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Darivatelj krvi ne može pristupiti podacima drugog darivatelja krvi.");
            }
            return ResponseEntity.ok(donationTryService.getDonationTryHistory(donorId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_DONOR", "ROLE_BANK_WORKER"})
    @GetMapping("/pdf/{donationId}")
    public ResponseEntity<Object> getSuccessfulDonationPdfCert(@PathVariable String donationId) {
        try {
            DonationTry donationTry = donationTryService.getDonationTryByDonationId(donationId);
            if (donationTry == null) throw new WrongDonationTryException("Ne postoji pokušaj doniranja s tim id-em.");
            if (currentUserUtil.getCurrentUserRole().equals("DONOR") && !currentUserUtil.checkIfCurrentUser(String.valueOf(donationTry.getDonor().getDonorId()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Darivatelj krvi ne može preuzeti potvrdu o doniranju drugog darivatelja krvi.");
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(donationTryService.generatePDFCertificateForSuccessfulDonation(donationId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_BANK_WORKER", "ROLE_DONOR"})
    @GetMapping("/last/{donorId}")
    public ResponseEntity<Object> getLastDonationDateForDonor(@PathVariable String donorId) {
        try {
            String role = currentUserUtil.getCurrentUserRole();
            if (role.equals("DONOR") && !currentUserUtil.checkIfCurrentUser(donorId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Darivatelj krvi ne može pristupiti podacima drugog darivatelja krvi.");
            }
            return ResponseEntity.ok(donationTryService.getLastDonationDateForDonor(donorId).format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_BANK_WORKER", "ROLE_DONOR"})
    @GetMapping("/days-until-next-donation/{donorId}")
    public ResponseEntity<Object> getWhenIsWaitingPeriodOverForDonor(@PathVariable String donorId) {
        try {
            String role = currentUserUtil.getCurrentUserRole();
            if (role.equals("DONOR") && !currentUserUtil.checkIfCurrentUser(donorId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Darivatelj krvi ne može pristupiti podacima drugog darivatelja krvi.");
            }
            return ResponseEntity.ok(donationTryService.getWhenIsWaitingPeriodOverForDonor(donorId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
