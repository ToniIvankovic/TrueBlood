package progi.megatron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.Donor;
import progi.megatron.model.User;
import progi.megatron.repository.DonorRepository;
import progi.megatron.util.Role;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DonorService(DonorRepository donorRepository, UserService userService) {
        this.donorRepository = donorRepository;
        this.userService = userService;
    }

    @Transactional(rollbackFor = Exception.class)
    public Donor createDonor(Donor donor){
        User user = new User(Role.DONOR, passwordEncoder.encode("generated password"));
        userService.createUser(user);
        donor.setDonorId(user.getUserId());
        donor.setBloodType(null);
        donor.setPermRejectedReason(null);
        // todo: send email
        return donorRepository.save(donor);
    }


}
