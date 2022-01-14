package progi.megatron.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DonationTryRequestDTO {

    private String rejectReason;
    @JsonProperty
    private boolean isReasonPerm;
    private String donationPlace;
    private String donorId;
    private String bankWorkerId;

}
