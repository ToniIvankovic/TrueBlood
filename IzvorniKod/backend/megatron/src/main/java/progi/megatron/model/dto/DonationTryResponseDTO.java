package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@Component
public class DonationTryResponseDTO {

    private Long donationId;
    private boolean successful;
    private String rejectedReason;
    private LocalDate donationDate;
    private String donationPlace;
    private Long donorId;

    public DonationTryResponseDTO() {
    }

    public DonationTryResponseDTO(Long donationId, boolean successful, String rejectedReason, LocalDate donationDate, String donationPlace, Long donorId) {
        this.donationId = donationId;
        this.successful = successful;
        this.rejectedReason = rejectedReason;
        this.donationDate = donationDate;
        this.donationPlace = donationPlace;
        this.donorId = donorId;
    }

}
