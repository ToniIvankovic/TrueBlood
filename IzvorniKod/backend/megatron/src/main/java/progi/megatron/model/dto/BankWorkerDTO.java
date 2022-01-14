package progi.megatron.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class BankWorkerDTO {

    private String firstName;
    private String lastName;
    private String oib;
    @JsonFormat(pattern="dd.MM.yyyy") private LocalDate birthDate;
    private String birthPlace;
    private String address;
    private String workPlace;
    private String privateContact;
    private String workContact;
    private String email;

}
