package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.BankWorker;

@Repository
@Transactional
public interface BankWorkerRepository extends JpaRepository<BankWorker, Long> {

    BankWorker getBankWorkerByOib(String oib);

    BankWorker getBankWorkerByBankWorkerId(Long bankWorkerId);

    BankWorker save(BankWorker bankWorker);

    // todo: delete bank worker

}
