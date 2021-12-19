package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;
import progi.megatron.model.User;
import progi.megatron.model.dto.DonorByBankWorkerDTO;
import progi.megatron.model.dto.DonorByDonorDTO;
import progi.megatron.model.dto.UserDTO;
import progi.megatron.repository.DonorRepository;
import progi.megatron.util.Role;
import progi.megatron.validation.DonorValidator;
import progi.megatron.validation.IdValidator;
import progi.megatron.validation.OibValidator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final UserService userService;
    private final DonorValidator donorValidator;
    private final IdValidator idValidator;
    private final OibValidator oibValidator;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public DonorService(DonorRepository donorRepository, UserService userService, DonorValidator donorValidator, IdValidator idValidator, OibValidator oibValidator, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.donorRepository = donorRepository;
        this.userService = userService;
        this.donorValidator = donorValidator;
        this.idValidator = idValidator;
        this.oibValidator = oibValidator;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    public Donor createDonorByDonor(DonorByDonorDTO donorByDonorDTO){
        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        user = userService.createUser(user);
        Donor donor = modelMapper.map(donorByDonorDTO, Donor.class);
        donor.setDonorId(user.getUserId());

        donorValidator.validateDonor(donor);
        if (getDonorByOib(donor.getOib()) != null) {
            throw new WrongDonorException("Donor with that oib already exists. ");
        }
        donor = donorRepository.save(donor);

        // todo: send email
        logger.info("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return donor;
    }

    public Donor createDonorByBankWorker(DonorByBankWorkerDTO donorByBankWorkerDTO){
        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        userService.createUser(user);
        Donor donor = modelMapper.map(donorByBankWorkerDTO, Donor.class);
        donor.setDonorId(user.getUserId());

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
        oibValidator.validateOib(oib);
        return donorRepository.getDonorByOib(oib);
    }

    public Donor getDonorByDonorId(String donorId){
        idValidator.validateId(donorId);
        return donorRepository.getDonorByDonorId(Long.valueOf(donorId));
    }

    public List<String> getOibsByFirstNameAndLastName(String firstName, String lastName){
        List<Donor> donors = donorRepository.getDonorByFirstNameAndLastName(firstName, lastName);
        return donors.stream().map(donor -> donor.getOib()).collect(Collectors.toList());
    }

}
