package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.model.User;
import progi.megatron.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

}
