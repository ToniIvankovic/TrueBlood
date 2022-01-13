package progi.megatron.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import progi.megatron.exception.InvalidTokenException;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonorByBankWorkerDTOWithoutId;
import progi.megatron.model.dto.DonorByDonorDTOWithId;
import progi.megatron.model.dto.DonorByDonorDTOWithoutId;
import progi.megatron.service.DonorService;
import progi.megatron.service.UserService;
import progi.megatron.util.CurrentUserUtil;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/donor")
public class DonorController {

    private static final String REDIRECT_LOGIN = "redirect:/login";

    private final UserService userService;
    private final DonorService donorService;
    private final CurrentUserUtil currentUserUtil;

    public DonorController(UserService userService, DonorService donorService, CurrentUserUtil currentUserUtil) {
        this.userService = userService;
        this.donorService = donorService;
        this.currentUserUtil = currentUserUtil;
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> createDonorByDonor(@RequestBody DonorByDonorDTOWithoutId donorByDonorDTOWithoutId) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!userId.equals("anonymousUser")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Već registrirani korisnik se ne može ponovo registrirati.");
            else return ResponseEntity.ok(donorService.createDonorByDonor(donorByDonorDTOWithoutId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/add-donor")
    public ResponseEntity<Object> createDonorByBankWorker(@RequestBody DonorByBankWorkerDTOWithoutId donorByBankWorkerDTOWithoutId) {
        try {
            return ResponseEntity.ok(donorService.createDonorByBankWorker(donorByBankWorkerDTOWithoutId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER"})
    @GetMapping("/oib/{oib}")
    public ResponseEntity<Object> getDonorByOib(@PathVariable String oib) {
        try {
            Donor donor = donorService.getNotDeactivatedDonorByOib(oib);
            if (donor == null) return ResponseEntity.ok("Ne postoji djelatnik krvi s tim id-em");
            else return ResponseEntity.ok(donor);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER", "ROLE_DONOR"})
    @GetMapping("/id/{donorId}")
    public ResponseEntity<Object> getDonorByDonorId(@PathVariable String donorId) {
        try {
            if (currentUserUtil.getCurrentUserRole().equals("DONOR") && !currentUserUtil.checkIfCurrentUser(donorId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Darivatelj krvi ne može pristupiti podacima drugog darivatelja krvi.");
            }
            Donor donor = donorService.getDonorByDonorId(donorId);
            if (donor == null) return ResponseEntity.ok("Ne postoji darivatelj krvi s tim id-em.");
            else return ResponseEntity.ok(donor);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER"})
    @GetMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<Object> getOibsByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            return ResponseEntity.ok(donorService.getOibsByFirstNameAndLastName(firstName, lastName));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER"})
    @GetMapping("/all")
    public ResponseEntity<Object> getDonorsAll(@RequestParam Integer resultsPerPage, @RequestParam Integer page) {
        try {
            return ResponseEntity.ok(donorService.getDonorsAll(resultsPerPage, page));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER"})
    @GetMapping
    public ResponseEntity<Object> getDonorsByAny(@RequestParam String query) {
        try {
            return ResponseEntity.ok(donorService.getDonorsByAny(query));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_DONOR"})
    @PostMapping("/update")
    public ResponseEntity<Object> updateDonorByDonor(@RequestBody DonorByDonorDTOWithId donorByDonorDTOWithId) {
        try {
            if (!currentUserUtil.checkIfCurrentUser(String.valueOf(donorByDonorDTOWithId.getDonorId()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Darivatelj krvi ne može uređivati podatke drugog darivatelja krvi.");
            }
            return ResponseEntity.ok(donorService.updateDonorByDonor(donorByDonorDTOWithId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/update-donor")
    public ResponseEntity<Object> updateDonorByBankWorker(@RequestBody Donor donor) {
        try {
            return ResponseEntity.ok(donorService.updateDonorByBankWorker(donor));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
