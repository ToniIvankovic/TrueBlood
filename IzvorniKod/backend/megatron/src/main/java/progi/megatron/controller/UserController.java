package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.User;
import progi.megatron.model.dto.UserDTO;
import progi.megatron.service.UserService;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserDTO userDTO;

    public UserController(UserService userService, UserDTO userDTO) {
        this.userService = userService;
        this.userDTO = userDTO;
    }

    @Secured({"ROLE_DONOR", "ROLE_WORKER"})
    @GetMapping
    public ResponseEntity<Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        try {
            User user = userService.findById(currentUsername);
            return ResponseEntity.ok(userDTO.userToUserDTO(user));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    // todo: activate account
}
