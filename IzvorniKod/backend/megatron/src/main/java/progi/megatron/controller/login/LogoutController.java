package progi.megatron.controller.login;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/logout")
public class LogoutController {

    @GetMapping
    public void logout(HttpServletRequest request) {
        // TODO: invalidate token
    }

}
