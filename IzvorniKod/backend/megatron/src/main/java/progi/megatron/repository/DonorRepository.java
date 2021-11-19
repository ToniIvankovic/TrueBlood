package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.Donor;

@Repository
@Transactional
public interface DonorRepository extends JpaRepository<Donor, Long> {

    // in service make sure that if the user is bank worker blood type and perm rejected are not null,
    // and if the user is donor blood type and perm rejected are null

    Donor save(Donor donor);

    Donor getDonorByOib(String oib);

    // todo: update donor
    // todo: check if donor permanently rejected
    // todo: get donor by id

}
