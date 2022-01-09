package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.BankWorker;

import java.util.List;

@Repository
@Transactional
public interface BankWorkerRepository extends JpaRepository<BankWorker, Long> {

    @Query(value = "SELECT bank_worker_id, first_name, last_name, oib, birth_date, birth_place, address, work_place, private_contact, work_contact, email " +
            " FROM bank_worker JOIN user_account ON bank_worker.bank_worker_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND bank_worker.oib = ?1", nativeQuery = true)
    BankWorker getNotDeactivatedBankWorkerByOib(String oib);

    BankWorker getBankWorkerByBankWorkerId(Long bankWorkerId);

    List<BankWorker> getBankWorkersByOibIsContaining(String oib);

    List<BankWorker> getBankWorkerByFirstNameIsContainingIgnoreCase(String oib);

    List<BankWorker> getBankWorkerByLastNameIsContainingIgnoreCase(String oib);

}
