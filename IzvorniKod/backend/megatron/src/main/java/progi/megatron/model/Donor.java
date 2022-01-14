package progi.megatron.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donor")
public class Donor implements Serializable {

    @Id
    private Long donorId;

    private String firstName;

    private String lastName;

    private String oib;

    private String gender;

    @JsonFormat(pattern="dd.MM.yyyy.")
    private LocalDate birthDate;

    private String birthPlace;

    private String address;

    private String workPlace;

    private String privateContact;

    private String workContact;

    private String email;

    private String bloodType;

    private String permRejectedReason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donor donor = (Donor) o;
        return Objects.equals(donorId, donor.donorId) && Objects.equals(firstName, donor.firstName) && Objects.equals(lastName, donor.lastName) && Objects.equals(oib, donor.oib) && Objects.equals(gender, donor.gender) && Objects.equals(birthDate, donor.birthDate) && Objects.equals(birthPlace, donor.birthPlace) && Objects.equals(address, donor.address) && Objects.equals(workPlace, donor.workPlace) && Objects.equals(privateContact, donor.privateContact) && Objects.equals(workContact, donor.workContact) && Objects.equals(email, donor.email) && Objects.equals(bloodType, donor.bloodType) && Objects.equals(permRejectedReason, donor.permRejectedReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donorId, firstName, lastName, oib, gender, birthDate, birthPlace, address, workPlace, privateContact, workContact, email, bloodType, permRejectedReason);
    }

}