package progi.megatron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.model.Donor;
import progi.megatron.model.User;
import progi.megatron.model.dto.DonorByDonorDTO;
import progi.megatron.repository.DonorRepository;
import progi.megatron.util.Role;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserService userService;
    private final DonorByDonorDTO donorByDonorDTO;
    //@Autowired
    private PasswordEncoder passwordEncoder;

    public DonorService(DonorRepository donorRepository, UserService userService, DonorByDonorDTO donorByDonorDTO, PasswordEncoder passwordEncoder) {
        this.donorRepository = donorRepository;
        this.userService = userService;
        this.donorByDonorDTO = donorByDonorDTO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = Exception.class)
    public Donor createDonorByDonor(DonorByDonorDTO donorByDonorDTO){
        User user = new User(Role.DONOR, passwordEncoder.encode("generated password"));
        userService.createUser(user);
        // todo: send email
        return donorRepository.save(donorByDonorDTO.DonorByDonorDTOToDonor(donorByDonorDTO, user.getUserId()));
    }

}
