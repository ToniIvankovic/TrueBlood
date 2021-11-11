package progi.megatron.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.User;
import progi.megatron.model.UserInfo;
import progi.megatron.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured({"ROLE_DONOR", "ROLE_WORKER"})
    @GetMapping
    public ResponseEntity<UserInfo> userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> user = userService.findById(currentUsername);
        if(!user.isPresent()) {
            throw new RuntimeException("User in context but not in repository.");
        }
        return ResponseEntity.ok(new UserInfo(String.valueOf(user.get().getUserId()), user.get().getUserRole()));
    }
}
