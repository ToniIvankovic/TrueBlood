package progi.megatron.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/donor")
public class DonorController {

    private static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    private final DonorService donorService;
    private final CurrentUserUtil currentUserUtil;

    public DonorController(DonorService donorService, CurrentUserUtil currentUserUtil) {
        this.donorService = donorService;
        this.currentUserUtil = currentUserUtil;
    }

    @GetMapping("/verify")
    public ResponseEntity verifyDonor(@RequestParam(name = "token") String token, final Model model, RedirectAttributes redirAttr) {
        if (StringUtils.isEmpty(token)) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale()));
        }
        try {
            System.out.println(token);
            userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null, LocaleContextHolder.getLocale()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        redirAttr.addFlashAttribute("verifiedAccountMsg", messageSource.getMessage("user.registration.verification.success", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.ok(token);
    }

    // todo: secured (no role)
    @PostMapping("/registration")
    public ResponseEntity<Object> createDonorByDonor(@RequestBody DonorByDonorDTOWithoutId donorByDonorDTOWithoutId, HttpServletRequest request) {
        System.out.println("USO BRATE");
        try {
            return ResponseEntity.ok(donorService.createDonorByDonor(donorByDonorDTOWithoutId));
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
            Donor donor = donorService.getDonorByOib(oib);
            if (donor == null) return ResponseEntity.ok("No donor with that oib found.");
            else return ResponseEntity.ok(donor);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER", "ROLE_DONOR"})
    @GetMapping("/id/{donorId}")
    public ResponseEntity<Object> getDonorByDonorId(@PathVariable String donorId, HttpServletRequest request) {
        try {
            if (currentUserUtil.getCurrentUserRole().equals("DONOR") && !currentUserUtil.checkIfCurrentUser(donorId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Donor can not fetch other donors.");
            }
            Donor donor = donorService.getDonorByDonorId(donorId);
            if (donor == null) return ResponseEntity.ok("No donor with that id found.");
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
    public ResponseEntity<Object> updateDonorByDonor(@RequestBody DonorByDonorDTOWithId donorByDonorDTOWithId, HttpServletRequest request) {
        try {
            String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!currentUserId.equals(donorByDonorDTOWithId.getDonorId().toString())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Donor can not update other donors.");
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
