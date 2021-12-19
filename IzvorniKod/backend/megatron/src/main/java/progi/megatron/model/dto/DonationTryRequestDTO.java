package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DonationTryRequestDTO {

    private String rejectReason;
    private String donorId;
    private String bankWorkerId;

    public DonationTryRequestDTO() {
    }

    public DonationTryRequestDTO(String rejectReason, String donorId, String bankWorkerId) {
        this.rejectReason = rejectReason;
        this.donorId = donorId;
        this.bankWorkerId = bankWorkerId;
    }

}
