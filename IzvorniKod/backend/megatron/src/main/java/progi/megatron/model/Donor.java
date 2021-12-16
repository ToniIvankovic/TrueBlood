package progi.megatron.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
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


    public Donor() { }

    public Donor(Long id, String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace,
                 String address, String workPlace, String privateContact, String workContact, String email,
                 String bloodType, String permRejectedReason) {
        this.donorId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
        this.workPlace = workPlace;
        this.privateContact = privateContact;
        this.workContact = workContact;
        this.email = email;
        this.bloodType = bloodType;
        this.permRejectedReason = permRejectedReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donor donor = (Donor) o;
        return Objects.equals(donorId, donor.donorId) && Objects.equals(firstName, donor.firstName) && Objects.equals(lastName, donor.lastName) && Objects.equals(oib, donor.oib) && Objects.equals(birthDate, donor.birthDate) && Objects.equals(birthPlace, donor.birthPlace) && Objects.equals(address, donor.address) && Objects.equals(workPlace, donor.workPlace) && Objects.equals(privateContact, donor.privateContact) && Objects.equals(workContact, donor.workContact) && Objects.equals(email, donor.email) && Objects.equals(bloodType, donor.bloodType) && Objects.equals(permRejectedReason, donor.permRejectedReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donorId, firstName, lastName, oib, birthDate, birthPlace, address, workPlace, privateContact, workContact, email, bloodType, permRejectedReason);
    }
}