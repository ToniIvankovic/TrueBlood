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

    Optional<User> getUserByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.accActivated = 1 WHERE u.userId = ?1")
    void activateUserAccount(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.permDeactivated = 1 WHERE u.userId = ?1")
    void deactivateUserAccount(Long userId);

}
