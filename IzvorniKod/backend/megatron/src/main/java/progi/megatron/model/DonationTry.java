package progi.megatron.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate donationDate;

    private String donationPlace;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "bank_worker_id")
    private BankWorker bankWorker;

    public DonationTry() {
    }

    public DonationTry(Long donationId, String rejectReason, LocalDate donationDate, String donationPlace, Donor donor, BankWorker bankWorker) {
        this.donationId = donationId;
        this.rejectReason = rejectReason;
        this.donationDate = donationDate;
        this.donationPlace = donationPlace;
        this.donor = donor;
        this.bankWorker = bankWorker;
    }

}
