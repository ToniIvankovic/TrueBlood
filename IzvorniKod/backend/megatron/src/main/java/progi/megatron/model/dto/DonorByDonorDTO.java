package progi.megatron.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

}
