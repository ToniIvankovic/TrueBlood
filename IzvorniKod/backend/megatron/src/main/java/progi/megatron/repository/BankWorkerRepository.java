package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.BankWorker;

import java.util.List;

@Repository
public interface BankWorkerRepository extends JpaRepository<BankWorker, Long> {

    @Query(value = "SELECT bank_worker_id, first_name, last_name, oib, birth_date, birth_place, address, work_place, private_contact, work_contact, email " +
            " FROM bank_worker JOIN user_account ON bank_worker.bank_worker_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND bank_worker.oib = ?1", nativeQuery = true)
    BankWorker getNotDeactivatedBankWorkerByOib(String oib);

    @Query(value = "SELECT bank_worker_id, first_name, last_name, oib, birth_date, birth_place, address, work_place, private_contact, work_contact, email " +
            " FROM bank_worker JOIN user_account ON bank_worker.bank_worker_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND bank_worker.bank_worker_id = ?1", nativeQuery = true)
    BankWorker getBankWorkerByBankWorkerId(Long bankWorkerId);

    @Query(value = "SELECT bank_worker_id, first_name, last_name, oib, birth_date, birth_place, address, work_place, private_contact, work_contact, email " +
            " FROM bank_worker JOIN user_account ON bank_worker.bank_worker_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND UPPER(bank_worker.oib) LIKE CONCAT(CONCAT('%', CAST(?1 AS text)), '%') ", nativeQuery = true)
    List<BankWorker> getBankWorkersByOibIsContaining(String oib);

    @Query(value = "SELECT bank_worker_id, first_name, last_name, oib, birth_date, birth_place, address, work_place, private_contact, work_contact, email " +
            " FROM bank_worker JOIN user_account ON bank_worker.bank_worker_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND UPPER(bank_worker.first_name) LIKE CONCAT(CONCAT('%', CAST(UPPER(?1) AS text)), '%') ", nativeQuery = true)
    List<BankWorker> getBankWorkerByFirstNameIsContainingIgnoreCase(String firstName);

    @Query(value = "SELECT bank_worker_id, first_name, last_name, oib, birth_date, birth_place, address, work_place, private_contact, work_contact, email " +
            " FROM bank_worker JOIN user_account ON bank_worker.bank_worker_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND UPPER(bank_worker.last_name) LIKE CONCAT(CONCAT('%', CAST(UPPER(?1) AS text)), '%') ", nativeQuery = true)
    List<BankWorker> getBankWorkerByLastNameIsContainingIgnoreCase(String lastName);

}
