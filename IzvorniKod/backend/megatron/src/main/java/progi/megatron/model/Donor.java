package progi.megatron.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "donor")
public class Donor implements Serializable {

    @Id
    private Long donorId;

    private String firstName;

    private String lastName;

    private String oib;

    private LocalDate birthDate;

    private String birthPlace;

    private String address;

    private String workPlace;

    private String privateContact;

    private String workContact;

    private String email;

    private String bloodType;

    private String permRejectedReason;

    private String lastDonationPlace;

}
