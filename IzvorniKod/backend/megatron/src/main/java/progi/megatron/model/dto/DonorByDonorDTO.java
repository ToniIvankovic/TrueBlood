package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import progi.megatron.model.Donor;
import java.time.LocalDate;

@Getter
@Setter
@Component
public class DonorByDonorDTO {

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

    public DonorByDonorDTO() {}

    public DonorByDonorDTO(String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace, String address, String workPlace, String privateContact, String workContact, String email) {
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

}
