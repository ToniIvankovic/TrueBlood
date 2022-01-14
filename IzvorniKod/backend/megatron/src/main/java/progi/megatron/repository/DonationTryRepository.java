package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.megatron.model.DonationTry;
import progi.megatron.model.Donor;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonationTryRepository extends JpaRepository<DonationTry, Long> {

    List<DonationTry> getDonationTryByDonor(Donor donor);

    DonationTry getDonationTryByDonationId(Long donationId);

    List<DonationTry> getDonationTryByDonationDate(LocalDate localDate);

}
