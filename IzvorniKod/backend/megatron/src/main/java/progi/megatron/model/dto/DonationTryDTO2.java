package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import progi.megatron.model.BankWorker;
import progi.megatron.model.Donor;

@Getter
@Setter
@Component
public class DonationTryDTO2 {

    private String rejectReason;
    private String bloodType;
    private Donor donor;
    private BankWorker bankWorker;

    public DonationTryDTO2(String rejectReason, String bloodType, Donor donor, BankWorker bankWorker) {
        this.rejectReason = rejectReason;
        this.bloodType = bloodType;
        this.donor = donor;
        this.bankWorker = bankWorker;
    }

}
