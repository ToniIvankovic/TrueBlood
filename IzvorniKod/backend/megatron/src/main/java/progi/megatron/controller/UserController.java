package progi.megatron.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.User;
import progi.megatron.model.dto.UserDTO;
import progi.megatron.service.UserService;
import progi.megatron.security.JwtTokenUtil;
import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserService userService, ModelMapper modelMapper, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Secured({"ROLE_DONOR", "ROLE_BANK_WORKER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {

        // todo: examine if there is a more apt method of passing current userId to this method than getting token from request header
        try {
            // This assumes header and token were both validated by passing through the JwtTokenFilter
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String token = header.split(" ")[1].trim();
            String userId = jwtTokenUtil.getUserId(token);
            User user = userService.findById(userId);
            return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @Secured({"ROLE_DONOR", "ROLE_BANK_WORKER", "ROLE_ADMIN"})
    @GetMapping("/activate")
    public ResponseEntity<Object> activateCurrentUser(HttpServletRequest request) {
        // todo: examine if there is a more apt method of passing current userId to this method than getting token from request header
        try {
            // This assumes header and token were both validated by passing through the JwtTokenFilter
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String token = header.split(" ")[1].trim();
            String userId = jwtTokenUtil.getUserId(token);
            Long longUserId = userService.activateUserAccount(userId);
            User user = userService.findById(userId);
            if (user.getPermDeactivated() == 1) return ResponseEntity.ok("This user is permanently deactivated.");
            if (longUserId == null) return ResponseEntity.ok("This user is already activated.");
            return ResponseEntity.ok(longUserId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/deactivate/{userId}")
    public ResponseEntity<Object> permDeactivateUser(@PathVariable String userId) {
        try {
            Long longUserId = userService.permDeactivateUserAccount(userId);
            if (longUserId == null) return ResponseEntity.ok("This user is already permanently deactivated.");
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

}
