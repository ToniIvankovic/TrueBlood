package progi.megatron.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        String password = authentication.getCredentials().toString();

        // check if user exists
        Optional<User> user = userService.findById(name);
        if(!user.isPresent()) { throw new UsernameNotFoundException("No user '" + name + "'"); }

        String encodedPassword = user.get().getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        // check password
        if(encoder.matches(password, encodedPassword)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            String authoritiesString;
            // tip: role names have to start with 'ROLE_'
            if(user.get().isDonor()) {
                authorities = commaSeparatedStringToAuthorityList("ROLE_DONOR");
            } else if(user.get().isWorker()) {
                authorities = commaSeparatedStringToAuthorityList("ROLE_WORKER");
            } else if(user.get().isAdmin()) {
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