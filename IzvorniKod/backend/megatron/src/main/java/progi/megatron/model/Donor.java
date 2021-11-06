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

    public Donor(User user, String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace,
                 String address, String workPlace, String privateContact, String workContact, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
        this.workPlace = workPlace;
        this.workContact = workContact;
        this.privateContact = privateContact;
        this.email = email;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setPermRejectedReason(String permRejectedReason) {
        this.permRejectedReason = permRejectedReason;
    }
}