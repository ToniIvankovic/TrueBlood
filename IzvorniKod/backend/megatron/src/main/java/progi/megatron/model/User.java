package progi.megatron.model;

import progi.megatron.util.Role;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_account")
public class User implements Serializable {

    // todo: make user sequence start at 100000, not 1
    // todo: validation

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long userId;

    private String userRole;

    private String password;

    private int accActivated;

    private int permDeactivated;

    private int optOut;

    public User() {
    }

    public User(Role userRole, String password) {
        this.userId = 100000L;
        this.userRole = userRole.toString();
        this.password = password;
        this.accActivated = 0;
        this.permDeactivated = 0;
        this.optOut = 0;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDonor() {
        return userRole.equals("DONOR");
    }

    public boolean isWorker() {
        return userRole.equals("WORKER");
    }

}
