package progi.megatron.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progi.megatron.exception.InvalidTokenException;
import progi.megatron.exception.WrongUserException;
import progi.megatron.model.SecureToken;
import progi.megatron.model.User;
import progi.megatron.repository.UserRepository;
import java.security.SecureRandom;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private SecureTokenService secureTokenService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findById(String userIdString) {
        isValidUserId(userIdString);
        User user = userRepository.getUserByUserId(Long.valueOf(userIdString)).orElseThrow(() -> new UsernameNotFoundException("No user '" + userIdString + "'"));
        return user;
    }

    public Long activateUserAccount(String userId) {
        isValidUserId(userId);
        Long longUserId = Long.valueOf(userId);
        User user = userRepository.getUserByUserId(longUserId).orElseThrow(() -> new WrongUserException("No user with that userId."));
        if (user.getAccActivated() == 1) return null;
        userRepository.activateUserAccount(longUserId);
        return longUserId;
    }

    public Long permDeactivateUserAccount(String userId) {
        isValidUserId(userId);
        Long longUserId = Long.valueOf(userId);
        User user = userRepository.getUserByUserId(longUserId).orElseThrow(() -> new WrongUserException("No user with that userId."));
        if (user.getPermDeactivated() == 1) return null;
        userRepository.deactivateUserAccount(longUserId);
        return Long.valueOf(userId);
    }

    private void isValidUserId(String id) {
        try {
            Long value = Long.valueOf(id);
        } catch(NumberFormatException exc) {
            throw new WrongUserException("User id is not numeric. ");
        }
    }

    /**
     * @return String with random chars from A-Z, a-z and 0-9
     */
    public String randomPassword(){
        final int size = 8;     //length of password
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom randomizer = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(chars.charAt(randomizer.nextInt(chars.length())));
        }

        return sb.toString();
    }

    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
            throw new InvalidTokenException("Token is not valid");
        }
        User user = userRepository.getUserByUserId(secureToken.getUser().getDonorId()).orElseThrow(() -> new UsernameNotFoundException("No user found"));
        if (Objects.isNull(user)) {
            return false;
        }
        user.setAccActivated(1);
        userRepository.activateUserAccount(user.getUserId()); // let’s same user details

        secureTokenService.removeToken(secureToken);
        return true;
    }

}
