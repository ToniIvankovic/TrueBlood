package progi.megatron.controller.login;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/logout")
public class LogoutController {

    @GetMapping
    public void logout() {
        SecurityContext context = SecurityContextHolder.getContext();
        if(context == null) { return; }
        context.setAuthentication(null);
        return;
    }

}
