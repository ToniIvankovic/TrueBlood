package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.megatron.model.BankWorker;

@Repository
public interface BankWorkerRepository extends JpaRepository<BankWorker, Long> {

    // todo: create bank worker
    // todo: delete bank worker

}
