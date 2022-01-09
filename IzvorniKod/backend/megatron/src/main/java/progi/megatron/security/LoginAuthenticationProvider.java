package progi.megatron.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import progi.megatron.model.User;
import progi.megatron.service.UserService;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginAuthenticationProvider(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        User user;
        try {
            user = userService.findNotDeactivatedUserById(name);
        } catch(Exception e) {
            throw new BadCredentialsException("User does not exist.");
        }

        String password = authentication.getCredentials().toString();
        String encodedPassword = user.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(password, encodedPassword)) {
            throw new BadCredentialsException("Incorrect username or password.");
        }

        String token = jwtTokenUtil.generateAccessToken(user);
        return new UsernamePasswordAuthenticationToken(name, token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
