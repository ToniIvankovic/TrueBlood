package progi.megatron.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.model.Donor;
import progi.megatron.model.User;
import progi.megatron.repository.DonorRepository;
import progi.megatron.util.Role;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserService userService;

    public DonorService(DonorRepository donorRepository, UserService userService) {
        this.donorRepository = donorRepository;
        this.userService = userService;
    }


    public void createDonor(Donor donor) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(Role.DONOR, encoder.encode("generated password"));
        userService.createUser(user);
        // todo: create donor
        // todo: send email
        return;
    }

}
