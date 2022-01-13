package progi.megatron.controller;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import progi.megatron.exception.InvalidTokenException;
import progi.megatron.model.User;
import progi.megatron.model.dto.PasswordChangeUserDTO;
import progi.megatron.model.dto.UserDTO;
import progi.megatron.service.UserService;
import progi.megatron.util.CurrentUserUtil;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CurrentUserUtil currentUserUtil;

    @Autowired
    private MessageSource messageSource;

    public UserController(UserService userService, ModelMapper modelMapper, CurrentUserUtil currentUserUtil) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.currentUserUtil = currentUserUtil;
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
        return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "https://trueblood-fe-dev.herokuapp.com/aktiviran_racun").build();
    }

    //@Secured({"ROLE_DONOR", "ROLE_BANK_WORKER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<Object> getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) auth.getAuthorities();
            if(authorities == null || authorities.isEmpty()) {
                return ResponseEntity.ok(null);
            }
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals("ROLE_ANONYMOUS")) {
                    return ResponseEntity.ok(null);
                }
            }
            String userId = auth.getPrincipal().toString();
            User user = userService.findNotDeactivatedUserById(userId);
            return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/deactivate/{userId}")
    public ResponseEntity<Object> permDeactivateUser(@PathVariable String userId) {
        try {
            Long longUserId = userService.permDeactivateUserAccount(userId);
            if (longUserId == null) return ResponseEntity.ok("Ovaj korisnik je trajno deaktiviran.");
            return ResponseEntity.ok(longUserId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/activated/{userId}")
    public ResponseEntity<Object> checkIfUserActivated(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(userService.checkIfUserActivated(userId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_DONOR"})
    @PostMapping("/password")
    public ResponseEntity<Object> changePassword(@RequestBody PasswordChangeUserDTO passwordChangeUserDTO) {
        try {
            if (!currentUserUtil.checkIfCurrentUser(String.valueOf(passwordChangeUserDTO.getUserId()))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Korisnik ne može promjeniti lozinku drugog korisnika.");
            }
            userService.changePassword(passwordChangeUserDTO);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
