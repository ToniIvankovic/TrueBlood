package progi.megatron.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.User;
import progi.megatron.model.dto.UserDTO;
import progi.megatron.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Secured({"ROLE_DONOR", "ROLE_BANK_WORKER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<Object> getCurrentUser() {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = userService.findNotDeactivatedUserById(userId);
            return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // todo: maybe add some sort of security for this
    @GetMapping("/activate/{userId}")
    public ResponseEntity<Object> activateUser(@PathVariable String userId) {
        try {
            Long longUserId = userService.activateUserAccount(userId);
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
