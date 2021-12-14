package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DonationTryDTO1 {

    private String rejectReason;
    private String bloodType;
    private String donorId;
    private String bankWorkerId;

    public DonationTryDTO1(String rejectReason, String bloodType, String donorId, String bankWorkerId) {
        this.rejectReason = rejectReason;
        this.bloodType = bloodType;
        this.donorId = donorId;
        this.bankWorkerId = bankWorkerId;
    }

}
