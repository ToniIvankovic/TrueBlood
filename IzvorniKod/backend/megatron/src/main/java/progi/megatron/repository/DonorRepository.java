package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.Donor;
import java.util.List;

@Repository
@Transactional
public interface DonorRepository extends JpaRepository<Donor, Long> {

    Donor save(Donor donor);

    Donor getDonorByOib(String oib);

    Donor getDonorByDonorId(Long donorId);

    List<Donor> getDonorByFirstNameAndLastName(String firstName, String lastName);

    List<Donor> getDonorsByOibIsContaining(String oib);

    List<Donor> getDonorsByFirstNameIsContaining(String firstName);

    List<Donor> getDonorsByLastNameIsContaining(String lastName);

    // todo: check if donor permanently rejected

}
