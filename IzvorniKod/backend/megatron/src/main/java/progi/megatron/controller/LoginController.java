package progi.megatron.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @GetMapping
    public void login() {
        // status 200 is automatically returned if basic auth is successful
    }
}
