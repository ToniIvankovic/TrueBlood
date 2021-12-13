package progi.megatron.controller;

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
    private final UserDTO userDTO;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserService userService, UserDTO userDTO, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.userDTO = userDTO;
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
            return ResponseEntity.ok(userDTO.userToUserDTO(user));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    // todo: activate account
}
