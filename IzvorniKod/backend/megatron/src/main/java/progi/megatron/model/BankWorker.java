package progi.megatron.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bank_worker")
public class BankWorker implements Serializable {

    @Id
    private Long bankWorkerId;

    private String firstName;

    private String lastName;

    private String oib;

    private String birthDate;

    private String birthPlace;

    private String address;

    private String workPlace;

    private String privateContact;

    private String workContact;

    private String email;

//    @OneToOne(cascade = CascadeType.ALL, optional = false)
//    @PrimaryKeyJoinColumn
//    private User user;

//   /* @OneToOne
//    @JoinColumn(name = "donation_try_bank_worker_id")
//    private DonationTry donationTry;*/
//
//    public BankWorker(User user, String firstName, String lastName, String oib, String birthDate, String birthPlace, String address, String workPlace, String privateContact, String workContact, String email) {
//        this.user = user;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.oib = oib;
//        this.birthDate = birthDate;
//        this.birthPlace = birthPlace;
//        this.address = address;
//        this.workPlace = workPlace;
//        this.privateContact = privateContact;
//        this.workContact = workContact;
//        this.email = email;
//    }
}
