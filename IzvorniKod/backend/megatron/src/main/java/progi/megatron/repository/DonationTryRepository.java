package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.megatron.model.DonationTry;

@Repository
public interface DonationTryRepository extends JpaRepository<DonationTry, Long> {

    DonationTry save(DonationTry donationTry);

}
