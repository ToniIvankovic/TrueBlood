package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT user_id, user_role, password, acc_activated, perm_deactivated, opt_out " +
            " FROM user_account " +
            " WHERE user_account.perm_deactivated = 0 " +
            " AND user_account.user_id = ?1", nativeQuery = true)
    Optional<User> getNotDeactivatedUserByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.accActivated = 1 WHERE u.userId = ?1")
    void activateUserAccount(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.permDeactivated = 1 WHERE u.userId = ?1")
    void deactivateUserAccount(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = ?2 WHERE u.userId = ?1")
    void changePassword(Long userId, String password);

}
