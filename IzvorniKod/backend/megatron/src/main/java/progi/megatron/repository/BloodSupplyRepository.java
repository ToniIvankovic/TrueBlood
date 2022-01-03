package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.BloodSupply;

@Repository
@Transactional
public interface BloodSupplyRepository extends JpaRepository<BloodSupply, String> {

    BloodSupply save(BloodSupply bloodSupply);

    @Transactional
    @Modifying
    @Query("UPDATE BloodSupply b SET b.numberOfUnits = ?2 WHERE b.bloodType = ?1")
    void manageBloodSupply(String bloodType, int numberOfUnits);

    BloodSupply getBloodSupplyByBloodType(String bloodType);



}
