package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.model.Donor;
import progi.megatron.model.User;
import progi.megatron.repository.DonorRepository;
import progi.megatron.util.Role;

import java.security.SecureRandom;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserService userService;

    public DonorService(DonorRepository donorRepository, UserService userService) {
        this.donorRepository = donorRepository;
        this.userService = userService;
    }

    public void createDonor(Donor donor) {
        User user = new User(Role.DONOR, "generated password");
        userService.createUser(user);
        // todo: create donor
        // todo: send email
        return;
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
