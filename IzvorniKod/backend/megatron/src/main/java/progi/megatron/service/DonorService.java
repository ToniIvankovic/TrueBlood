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
        User user = new User(Role.DONOR);
        userService.createUser(user);
        // todo: create donor
        // todo: send email
    }






}
