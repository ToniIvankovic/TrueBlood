package progi.megatron.model;

import javax.persistence.*;

@Entity
@Table(name = "donation_try")
public class DonationTry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DONATION_SEQ")
    @SequenceGenerator(name = "DONATION_SEQ", sequenceName = "DONATION_SEQ", allocationSize = 1)
    private Long donationId;

    private String rejectReason;

    private String bloodType;

    @ManyToOne
    private Donor donor;

    @ManyToOne
    private BankWorker bankWorker;

}