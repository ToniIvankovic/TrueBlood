package progi.megatron.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progi.megatron.exception.WrongUserIdException;
import progi.megatron.model.User;
import progi.megatron.model.dto.UserDTO;
import progi.megatron.repository.UserRepository;
import java.security.SecureRandom;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTO userDTO;

    public UserService(UserRepository userRepository, UserDTO userDTO) {
        this.userRepository = userRepository;
        this.userDTO = userDTO;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findById(String userIdString) {
        isValidUserId(userIdString);
        User user = userRepository.getUserByUserId(Long.valueOf(userIdString)).orElseThrow(() -> new UsernameNotFoundException("No user '" + userIdString + "'"));
        return user;
    }

    private void isValidUserId(String id) {
        try {
            Long value = Long.valueOf(id);
        } catch(NumberFormatException exc) {
            throw new WrongUserIdException();
        }
    }

    /**
     * @return String with random chars from A-Z, a-z and 0-9
     */
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
