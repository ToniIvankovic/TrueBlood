package progi.megatron.service;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.exception.InvalidTokenException;
import progi.megatron.exception.WrongUserException;
import progi.megatron.model.SecureToken;
import progi.megatron.model.User;
import progi.megatron.model.dto.PasswordChangeUserDTO;
import progi.megatron.model.dto.UserActivationDTO;
import progi.megatron.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SecureTokenService secureTokenService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, SecureTokenService secureTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.secureTokenService = secureTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findNotDeactivatedUserById(String userIdString) {
        isValidUserId(userIdString);
        User user = userRepository.getNotDeactivatedUserByUserId(Long.valueOf(userIdString)).orElseThrow(() -> new UsernameNotFoundException("Ne postoji korisnik s tim id-em."));
        return user;
    }

    public Long activateUserAccount(String userId) {
        isValidUserId(userId);
        Long longUserId = Long.valueOf(userId);
        User user = userRepository.getNotDeactivatedUserByUserId(longUserId).orElseThrow(() -> new WrongUserException("Ne postoji korisnik s tim id-em."));
        if (user.getAccActivated() == 1) return null;
        userRepository.activateUserAccount(longUserId);
        return longUserId;
    }

    public Long permDeactivateUserAccount(String userId) {
        isValidUserId(userId);
        Long longUserId = Long.valueOf(userId);
        User user = userRepository.getNotDeactivatedUserByUserId(longUserId).orElseThrow(() -> new WrongUserException("Ne postoji korisnik s tim id-em."));
        if (user.getPermDeactivated() == 1) return null;
        userRepository.deactivateUserAccount(longUserId);
        return Long.valueOf(userId);
    }

    private void isValidUserId(String id) {
        try {
            Long value = Long.valueOf(id);
        } catch (NumberFormatException exc) {
            throw new WrongUserException("Id korisnik nije numerički.");
        }
    }

    /**
     * @return String with random chars from A-Z, a-z and 0-9
     */
    public String randomPassword() {
        final int size = 8;     //length of password
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom randomizer = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append(chars.charAt(randomizer.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public void verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
            throw new InvalidTokenException("Token za verifikaciju korisnika nije validan.");
        }
        User user = userRepository.getNotDeactivatedUserByUserId(secureToken.getUserId()).orElseThrow(() -> new UsernameNotFoundException("Ne postoji korisnik s tim id-em."));
        if (Objects.isNull(user)) {
            return;
        }
        user.setAccActivated(1);
        userRepository.activateUserAccount(user.getUserId()); // let’s same user details

        secureTokenService.removeToken(secureToken);
    }

    public UserActivationDTO checkIfUserActivated(String userId) {
        User user = findNotDeactivatedUserById(userId);
        if (user == null) throw new WrongUserException("Ne postoji korisnik s tim id-em.");
        return modelMapper.map(user, UserActivationDTO.class);
    }

    public void changePassword(PasswordChangeUserDTO passwordChangeUserDTO) {
        User user = findNotDeactivatedUserById(String.valueOf(passwordChangeUserDTO.getUserId()));
        if (user == null) throw new WrongUserException("Ne postoji korisnik s tim id-em.");
        // validate password
        userRepository.changePassword(passwordChangeUserDTO.getUserId(), passwordEncoder.encode(passwordChangeUserDTO.getPassword()));
    }

}
