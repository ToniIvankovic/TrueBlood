package progi.megatron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.model.User;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
public class UserUserDetailsService implements UserDetailsService {
    @Value("${progi.admin.password}")
    private String adminPasswordHash;

    @Autowired
    private UserService userService; // todo : instancirat plz

    public UserUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new org.springframework.security.core.userdetails.User(username, adminPasswordHash, authorities(username));
    }

    private List<GrantedAuthority> authorities(String username) {
        if ("admin".equals(username))
            return commaSeparatedStringToAuthorityList("ROLE_ADMIN");

        User user = userService.findById(username).orElseThrow(
                () -> new UsernameNotFoundException("No user '" + username + "'")
        );

        if (user.isDonor()) {
            return commaSeparatedStringToAuthorityList("ROLE_DONOR");
        } else if(user.isWorker()) {
            return commaSeparatedStringToAuthorityList("ROLE_WORKER");
        } else {
            return NO_AUTHORITIES;
        }
    }
}

