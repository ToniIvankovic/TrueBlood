package progi.megatron.controller.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import progi.megatron.controller.UserController;
import progi.megatron.security.LoggedInResponse;
import progi.megatron.model.User;
import progi.megatron.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/login")
public class LoginController {

//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final UserController userController;

    public LoginController(UserService userService, UserController userController) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userController = userController;
    }

    //@Secured({"ROLE_DONOR", "ROLE_BANK_WORKER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<? extends Object> login() {//@RequestBody AuthRequest request) {
        try {
//            Authentication authenticate = authenticationManager
//                    .authenticate(
//                            new UsernamePasswordAuthenticationToken(
//                                    request.getUserId(), request.getPassword()
//                            )
//                    );

            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findNotDeactivatedUserById(userId);

            // todo: uncomment this after activation link is finished
            //if (user.getAccActivated() != 1) throw new UserNotActivatedException("Account not activated");
            //if (user.getPermDeactivated() != 0) throw new UserNotActivatedException("Account is permanently deactivated.");

//            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, HttpHeaders.ALL);
//            responseHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
//            responseHeaders.add(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user));

            return ResponseEntity.ok()
                    //.headers(responseHeaders)
                    .body(user.getUserId());
        } catch (BadCredentialsException ex) {   // | UserNotActivatedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

    }

    @Secured({"ROLE_DONOR", "ROLE_BANK_WORKER", "ROLE_ADMIN"})
    @GetMapping("/logged-in")
    public ResponseEntity<LoggedInResponse> loggedIn() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return ResponseEntity.ok(new LoggedInResponse(false));
        Authentication authentication = context.getAuthentication();
        if (authentication == null) return ResponseEntity.ok(new LoggedInResponse(false));
        if (authentication.getAuthorities().isEmpty()) return ResponseEntity.ok(new LoggedInResponse(false));
        return ResponseEntity.ok(new LoggedInResponse(true));
    }

}
