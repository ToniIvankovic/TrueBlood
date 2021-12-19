package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class DonationTryResponseDTO {

    private Long donationId;
    private boolean successful;
    private String rejectedReason;
    private Long donorId;

    public DonationTryResponseDTO() {
    }

    public DonationTryResponseDTO(Long donationId, boolean successful, String rejectedReason, Long donorId) {
        this.donationId = donationId;
        this.successful = successful;
        this.rejectedReason = rejectedReason;
        this.donorId = donorId;
    }

}
