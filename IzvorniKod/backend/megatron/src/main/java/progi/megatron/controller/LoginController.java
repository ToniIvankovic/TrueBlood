package progi.megatron.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.LoggedInResponse;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @GetMapping
    public void login() {
        // status 200 is automatically returned if basic auth is successful
    }

    @GetMapping("/logged_in")
    public ResponseEntity<LoggedInResponse> loggedIn() {
        SecurityContext context = SecurityContextHolder.getContext();
        if(context == null) return ResponseEntity.ok(new LoggedInResponse(false));
        Authentication authentication = context.getAuthentication();
        if(authentication == null) return ResponseEntity.ok(new LoggedInResponse(false));
        if(authentication.getAuthorities().isEmpty()) return ResponseEntity.ok(new LoggedInResponse(false));
        return ResponseEntity.ok(new LoggedInResponse(true));
    }
}
