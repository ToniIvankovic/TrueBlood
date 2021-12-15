package progi.megatron.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "donation_try")
public class DonationTry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DONATION_SEQ")
    @SequenceGenerator(name = "DONATION_SEQ", sequenceName = "DONATION_SEQ", allocationSize = 1)
    private Long donationId;

    private String rejectReason;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "bank_worker_id")
    private BankWorker bankWorker;

    public DonationTry() {
    }

    public DonationTry(Long donationId, String rejectReason, Donor donor, BankWorker bankWorker) {
        this.donationId = donationId;
        this.rejectReason = rejectReason;
        this.donor = donor;
        this.bankWorker = bankWorker;
    }

}
