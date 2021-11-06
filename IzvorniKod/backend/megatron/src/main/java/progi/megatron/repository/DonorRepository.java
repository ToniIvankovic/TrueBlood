package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import progi.megatron.model.Donor;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    // in service make sure that if the user is bank worker blood type and perm rejected are not null,
    // and if the user is donor blood type and perm rejected are null

    Donor save(Donor donor);

    // todo: update donor
    // todo: check if donor permanently rejected
    // todo: get donor by id

}
