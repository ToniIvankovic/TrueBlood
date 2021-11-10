package progi.megatron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progi.megatron.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    Optional<User> getUserByUserId(Long userId);

    // todo: activate user
    // todo: deactivate user
    // todo: change password

}
