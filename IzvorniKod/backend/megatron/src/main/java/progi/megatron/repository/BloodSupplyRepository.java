package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.BloodSupply;
import progi.megatron.model.User;

import java.util.Optional;

@Repository
@Transactional
public interface BloodSupplyRepository extends JpaRepository<BloodSupply, String> {

    BloodSupply save(BloodSupply bloodSupply);

    BloodSupply getBloodSupplyByBloodType(String bloodType);

    // todo: send blood to hospital

    // todo: set blood limits - max and min

}
