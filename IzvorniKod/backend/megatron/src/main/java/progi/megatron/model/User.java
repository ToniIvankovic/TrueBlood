package progi.megatron.model;

import lombok.Getter;
import lombok.Setter;
import progi.megatron.util.Role;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
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


    public User() { }

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

    public Boolean isWorker() {
        return userRole.equals("WORKER");
    }

}
