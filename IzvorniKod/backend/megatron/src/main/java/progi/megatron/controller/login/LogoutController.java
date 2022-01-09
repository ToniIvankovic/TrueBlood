package progi.megatron.controller.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progi.megatron.controller.UserController;
import javax.servlet.http.HttpServletRequest;

//@RestController
//@CrossOrigin
//@RequestMapping("/api/v1/logout")
//public class LogoutController {
//
//    private final UserController userController;
//
//    public LogoutController(UserController userController) {
//        this.userController = userController;
//    }
//
//    @GetMapping
//    public ResponseEntity<? extends Object> logout(HttpServletRequest request) {
//        // todo: invalidate token
//        return ResponseEntity.ok("Logged out.");
//    }
//
//}
