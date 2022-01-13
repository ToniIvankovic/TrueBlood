package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.Donor;
import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    Donor getDonorByOib(String oib);

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
                    " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
                    " WHERE user_account.perm_deactivated = 0 " +
                    " AND donor.oib = ?1", nativeQuery = true)
    Donor getNotDeactivatedDonorByOib(String oib);

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
            " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND donor.donor_id = ?1", nativeQuery = true)
    Donor getNotDeactivatedDonorByDonorId(Long donorId);

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
            " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND donor.first_name = ?1 AND donor.last_name = ?2 ", nativeQuery = true)
    List<Donor> getNotDeactivatedDonorByFirstNameAndLastName(String firstName, String lastName);

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
            " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND oib LIKE  CONCAT(CONCAT('%', CAST(?1 AS text)), '%')", nativeQuery = true)
    List<Donor> getDonorsByOibIsContaining(String oib);

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
            " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND UPPER(donor.first_name) LIKE CONCAT(CONCAT('%', CAST(UPPER(?1) AS text)), '%')", nativeQuery = true)
    List<Donor> getDonorByFirstNameIsContainingIgnoreCase(String firstName);

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
            " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND UPPER(donor.last_name) LIKE CONCAT(CONCAT('%', CAST(UPPER(?1) AS text)), '%')", nativeQuery = true)
    List<Donor> getDonorByLastNameIsContainingIgnoreCase(String lastName);

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
            " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 ", nativeQuery = true)
    List<Donor> getAllNotDeactivatedDonors();

    @Query(value = "SELECT donor_id, first_name, last_name, oib, gender, birth_date, birth_place, address, work_place, private_contact, work_contact, email, blood_type, perm_rejected_reason " +
            " FROM donor JOIN user_account ON donor.donor_id = user_account.user_id " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND donor.blood_type = ?1", nativeQuery = true)
    List<Donor> getDonorsByBloodType(String bloodType);

}
