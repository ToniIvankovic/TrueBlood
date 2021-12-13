package progi.megatron.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;
import progi.megatron.model.User;
import progi.megatron.model.dto.DonorByBankWorkerDTO;
import progi.megatron.model.dto.DonorByDonorDTO;
import progi.megatron.repository.DonorRepository;
import progi.megatron.util.Role;
import progi.megatron.validation.DonorValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserService userService;
    private final DonorValidator donorValidator;
    private final PasswordEncoder passwordEncoder;

    public DonorService(DonorRepository donorRepository, UserService userService, DonorValidator donorValidator, PasswordEncoder passwordEncoder) {
        this.donorRepository = donorRepository;
        this.userService = userService;
        this.donorValidator = donorValidator;
        this.passwordEncoder = passwordEncoder;
    }

    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    @Transactional(rollbackFor = Exception.class)
    public Donor createDonorByDonor(DonorByDonorDTO donorByDonorDTO){
        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        user = userService.createUser(user);
        Donor donor = donorByDonorDTO.DonorByDonorDTOToDonor(donorByDonorDTO, user.getUserId());

        donorValidator.validateDonor(donor);
        if (getDonorByOib(donor.getOib()) != null) {
            throw new WrongDonorException("Donor with that oib already exists. ");
        }
        donor = donorRepository.save(donor);

        // todo: send email
        logger.info("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return donor;
    }

    @Transactional(rollbackFor = Exception.class)
    public Donor createDonorByBankWorker(DonorByBankWorkerDTO donorByBankWorkerDTO){
        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        userService.createUser(user);
        Donor donor = donorByBankWorkerDTO.DonorByBankWorkerDTOToDonor(donorByBankWorkerDTO, user.getUserId());

        donorValidator.validateDonor(donor);
        if (getDonorByOib(donor.getOib()) != null) {
            throw new WrongDonorException("Donor with that oib already exists. ");
        }
        donor = donorRepository.save(donor);

        // todo: send email
        logger.info("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return donor;
    }

    public Donor getDonorByOib(String oib){
        donorValidator.validateOib(oib);
        return donorRepository.getDonorByOib(oib);
    }

    public Donor getDonorByDonorId(Long donorId){
        return donorRepository.getDonorByDonorId(Long.valueOf(donorId));
    }

    public List<String> getOibsByFirstNameAndLastName(String firstName, String lastName){
        List<Donor> donors = donorRepository.getDonorByFirstNameAndLastName(firstName, lastName);
        return donors.stream().map(donor -> donor.getOib()).collect(Collectors.toList());
    }

}
