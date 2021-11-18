package progi.megatron.controller.login;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.LoggedInResponse;
import progi.megatron.model.User;
import progi.megatron.model.dto.AuthRequest;
import progi.megatron.service.UserService;
import progi.megatron.util.JwtTokenUtil;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<? extends Object> login(@RequestBody AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUserId(), request.getPassword()
                            )
                    );

            String userId = authenticate.getPrincipal().toString();
            User user = userService.findById(userId);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
            responseHeaders.add(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user));

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(user.getUserId());
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Secured({"ROLE_DONOR", "ROLE_BANK_WORKER", "ROLE_ADMIN"})
    @GetMapping("/logged_in")
    public ResponseEntity<LoggedInResponse> loggedIn() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return ResponseEntity.ok(new LoggedInResponse(false));
        Authentication authentication = context.getAuthentication();
        if (authentication == null) return ResponseEntity.ok(new LoggedInResponse(false));
        if (authentication.getAuthorities().isEmpty()) return ResponseEntity.ok(new LoggedInResponse(false));
        return ResponseEntity.ok(new LoggedInResponse(true));
    }

}
