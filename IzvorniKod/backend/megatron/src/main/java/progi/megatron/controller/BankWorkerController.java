package progi.megatron.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import progi.megatron.exception.InvalidTokenException;
import progi.megatron.model.BankWorker;
import progi.megatron.model.dto.BankWorkerDTO;
import progi.megatron.service.BankWorkerService;
import progi.megatron.service.UserService;
import progi.megatron.util.CurrentUserUtil;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/bank-worker")
public class BankWorkerController {

    private final BankWorkerService bankWorkerService;
    private final CurrentUserUtil currentUserUtil;
    private final UserService userService;

    @Autowired
    private MessageSource messageSource;

    public BankWorkerController(BankWorkerService bankWorkerService, CurrentUserUtil currentUserUtil, UserService userService) {
        this.bankWorkerService = bankWorkerService;
        this.currentUserUtil = currentUserUtil;
        this.userService = userService;
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/add-bank-worker")
    public ResponseEntity<Object> createBankWorker(@RequestBody BankWorkerDTO bankWorkerDTO) {
        try {
            return ResponseEntity.ok(bankWorkerService.createBankWorker(bankWorkerDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity verifyDonor(@RequestParam(name = "token") String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("user.registration.verification.missing.token", null, LocaleContextHolder.getLocale()));
        }
        try {
            System.out.println(token);
            userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(token);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/oib/{oib}")
    public ResponseEntity<Object> getBankWorkerByOib(@PathVariable String oib) {
        try {
            BankWorker bankWorker = bankWorkerService.getBankWorkerByOib(oib);
            if (bankWorker == null) return ResponseEntity.ok("Ne postoji djelatinik banke s tim id-em.");
            return ResponseEntity.ok(bankWorker);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN","ROLE_BANK_WORKER"})
    @GetMapping("/id/{bankWorkerId}")
    public ResponseEntity<Object> getBankWorkerByBankWorkerId(@PathVariable String bankWorkerId) {
        try {
            BankWorker bankWorker = bankWorkerService.getBankWorkerByBankWorkerId(bankWorkerId);
            if (bankWorker == null) return ResponseEntity.ok("Ne postoji djelatinik banke s tim id-em.");
            return ResponseEntity.ok(bankWorker);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<Object> getBankWorkersByAny(@RequestParam String query) {
        try {
            return ResponseEntity.ok(bankWorkerService.getBankWorkersByAny(query));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/update")
    public ResponseEntity<Object> updateBankWorkerByBankWorker(@RequestBody BankWorker bankWorkerId) {
        try {
            String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!currentUserId.equals(bankWorkerId.getBankWorkerId().toString())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Djelatnik banke ne može uređivati podatke drugog djelatnika banke.");
            }
            return ResponseEntity.ok(bankWorkerService.updateBankWorkerByBankWorker(bankWorkerId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
