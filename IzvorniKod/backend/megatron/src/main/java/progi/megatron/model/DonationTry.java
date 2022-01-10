package progi.megatron.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donation_try")
public class DonationTry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DONATION_SEQ")
    @SequenceGenerator(name = "DONATION_SEQ", sequenceName = "DONATION_SEQ", allocationSize = 1)
    private Long donationId;

    private String rejectReason;

    @JsonFormat(pattern="dd.MM.yyyy")
    private LocalDate donationDate;

    private String donationPlace;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "bank_worker_id")
    private BankWorker bankWorker;

}
