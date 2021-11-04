package progi.megatron.service;

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
        User user = new User(Role.DONOR, "generated password");
        userService.createUser(user);
        // todo: create donor
        // todo: send email
        return;
    }

}
