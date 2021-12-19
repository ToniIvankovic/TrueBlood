package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@Component
public class DonationTryRequestDTO {

    private String rejectReason;
    private String donationPlace;
    private String donorId;
    private String bankWorkerId;

    public DonationTryRequestDTO() {
    }

    public DonationTryRequestDTO(String rejectReason, String donationPlace, String donorId, String bankWorkerId) {
        this.rejectReason = rejectReason;
        this.donationPlace = donationPlace;
        this.donorId = donorId;
        this.bankWorkerId = bankWorkerId;
    }

}
