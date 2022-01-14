package progi.megatron.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import progi.megatron.util.Role;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", initialValue = 10000, allocationSize = 1)
    private Long userId;

    private String userRole;

    private String password;

    private int accActivated;

    private int permDeactivated;

    private int optOut;

    public User(Role userRole, String password) {
        this.userRole = userRole.toString();
        this.password = password;
        this.accActivated = 0;
        this.permDeactivated = 0;
        this.optOut = 0;
    }

    public boolean isAdmin() { return userRole.equals("ADMIN"); }

    public Boolean isDonor() {
        return userRole.equals("DONOR");
    }

    public Boolean isBankWorker() {
        return userRole.equals("BANK_WORKER");
    }

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities;
        if(isDonor()) {
            authorities = commaSeparatedStringToAuthorityList("ROLE_DONOR");
        } else if(isBankWorker()) {
            authorities = commaSeparatedStringToAuthorityList("ROLE_BANK_WORKER");
        } else if(isAdmin()) {
            authorities = commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        } else {
            authorities = NO_AUTHORITIES;
        }
        return authorities;
    }

}
