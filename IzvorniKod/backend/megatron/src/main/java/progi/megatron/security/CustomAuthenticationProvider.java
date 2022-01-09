package progi.megatron.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import progi.megatron.model.User;
import progi.megatron.service.UserService;
import java.util.*;
import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password;
        try {
            password = authentication.getCredentials().toString();
        } catch(NullPointerException ex) {
            return null;
        }

        // check if user exists
        User user = userService.findNotDeactivatedUserById(name);

        String encodedPassword = user.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        // check password
        if(encoder.matches(password, encodedPassword)) {
            List<GrantedAuthority> authorities;
            // tip: role names have to start with 'ROLE_'
            if(user.isDonor()) {
                authorities = commaSeparatedStringToAuthorityList("ROLE_DONOR");
            } else if(user.isBankWorker()) {
                authorities = commaSeparatedStringToAuthorityList("ROLE_BANK_WORKER");
            } else if(user.isAdmin()) {
                authorities = commaSeparatedStringToAuthorityList("ROLE_ADMIN");
            } else {
                authorities = NO_AUTHORITIES;
            }
            return new UsernamePasswordAuthenticationToken(
                    name, password, authorities);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}