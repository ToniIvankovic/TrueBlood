package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import progi.megatron.model.BloodSupply;

@Repository
public interface BloodSupplyRepository extends JpaRepository<BloodSupply, String> {

    // todo: send blood to hospital
    // todo: set blood limits - max and min

}
