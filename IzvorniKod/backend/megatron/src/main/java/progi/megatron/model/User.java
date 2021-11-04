package progi.megatron.model;

import progi.megatron.util.Role;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_account")
public class User implements Serializable {

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

    //    @OneToOne(cascade = CascadeType.ALL, optional = true, mappedBy = "user")
//    //@JoinColumn(name = "bankWorkerId")
//    private BankWorker bankWorker;
//
//    @OneToOne(cascade = CascadeType.ALL, optional = true, mappedBy = "user")
//    //@JoinColumn(name = "donorId")
//    private Donor donor;
//
//    public Long getUserId() {
//        return userId;
//    }
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//    public Role getUserRole() {
//        return userRole;
//    }
//    public void setUserRole(Role userRole) {
//        this.userRole = userRole;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public User(){
//
//    }
//
//    public User(Long userId, Role userRole, String password, int accActivated, int permDeactivated, int optOut) {
//        this.userId = userId;
//        this.userRole = userRole;
//        this.password = password;
//        this.accActivated = accActivated;
//        this.permDeactivated = permDeactivated;
//        this.optOut = optOut;
//    }
//
//    public static User newUser(Role role){
//        return new User(1000000L, role,null,0,0,0);
//
//    }

}