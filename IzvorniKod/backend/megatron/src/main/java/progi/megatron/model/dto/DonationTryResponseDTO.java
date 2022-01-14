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
public class DonationTryResponseDTO {

    private Long donationId;
    private boolean successful;
    private String rejectedReason;
    @JsonFormat(pattern="dd.MM.yyyy") private LocalDate donationDate;
    private String donationPlace;
    private Long donorId;

}
