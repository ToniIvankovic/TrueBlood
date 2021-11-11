package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.model.User;
import progi.megatron.repository.UserRepository;

import java.security.SecureRandom;
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

    public Optional<User> findById(String userIdString) {
        if(!isValidUserId(userIdString)) { return null; }
        return userRepository.getUserByUserId(Long.valueOf(userIdString));
    }

    private boolean isValidUserId(String id) {
        try {
            Long value = Long.valueOf(id);
        } catch(NumberFormatException exc) {
            return false;
        }
        return true;
    }

    //returns String with random chars from A-Z, a-z and 0-9
    public static String randomPassword(){
        final int size = 8;     //length of password
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom randomizer = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(chars.charAt(randomizer.nextInt()));
        }

        return sb.toString();
    }

}
