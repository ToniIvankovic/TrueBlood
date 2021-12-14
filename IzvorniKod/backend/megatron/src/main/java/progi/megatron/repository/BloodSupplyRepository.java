package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.BloodSupply;
import progi.megatron.model.User;

import java.util.Optional;

@Repository
@Transactional
public interface BloodSupplyRepository extends JpaRepository<BloodSupply, String> {

    @Transactional
    @Modifying
    @Query("UPDATE BloodSupply b SET b.numberOfUnits = ?2 WHERE b.bloodType = ?1")
    void donateBlood(String bloodType, int numberOfUnits);

    BloodSupply getBloodSupplyByBloodType(String bloodType);

    // todo: send blood to hospital

    // todo: set blood limits - max and min

}
