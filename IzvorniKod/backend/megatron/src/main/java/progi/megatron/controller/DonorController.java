package progi.megatron.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import progi.megatron.exception.InvalidTokenException;
import progi.megatron.model.dto.DonorByBankWorkerDTO;
import progi.megatron.model.dto.DonorByDonorDTO;
import progi.megatron.service.DonorService;
import progi.megatron.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/donor")
public class DonorController {

    private static final String REDIRECT_LOGIN= "redirect:/login";

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    // todo: secured (no role)
    @PostMapping("/registration")
    public ResponseEntity<Object> createDonorByDonor(@RequestBody DonorByDonorDTO donorByDonorDTO) {
        try {
            return ResponseEntity.ok(donorService.createDonorByDonor(donorByDonorDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @GetMapping("/verify")
    public String verifyDonor(@RequestParam(required = false) String token, final Model model, RedirectAttributes redirAttr){
        if(StringUtils.isEmpty(token)){
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }
        try {
            userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            redirAttr.addFlashAttribute("tokenError", messageSource.getMessage("user.registration.verification.invalid.token", null,LocaleContextHolder.getLocale()));
            return REDIRECT_LOGIN;
        }

        redirAttr.addFlashAttribute("verifiedAccountMsg", messageSource.getMessage("user.registration.verification.success", null,LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }

    //@Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/add-donor")
    public ResponseEntity<Object> createDonorByBankWorker(@RequestBody DonorByBankWorkerDTO donorByBankWorkerDTO) {
        try {
            return ResponseEntity.ok(donorService.createDonorByBankWorker(donorByBankWorkerDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER"})
    @GetMapping("/oib/{oib}")
    public ResponseEntity<Object> getDonorByOib(@PathVariable String oib) {
        try {
            return ResponseEntity.ok(donorService.getDonorByOib(oib));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER", "ROLE_DONOR"})
    @GetMapping("/id/{donorId}")
    public ResponseEntity<Object> getDonorByDonorId(@PathVariable String donorId) {
        try {
            return ResponseEntity.ok(donorService.getDonorByDonorId(donorId));
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
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_BANK_WORKER"})
    @GetMapping
    public ResponseEntity<Object> getDonorsByAny(@RequestParam String query) {
        try {
            return ResponseEntity.ok(donorService.getDonorsByAny(query));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
