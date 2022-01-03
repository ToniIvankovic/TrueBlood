package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.email.AccountVerificationEmailContext;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;
import progi.megatron.model.SecureToken;
import progi.megatron.model.User;
import progi.megatron.model.dto.DonorByBankWorkerDTOWithoutId;
import progi.megatron.model.dto.DonorByDonorDTOWithId;
import progi.megatron.model.dto.DonorByDonorDTOWithoutId;
import progi.megatron.repository.DonorRepository;
import progi.megatron.repository.SecureTokenRepository;
import progi.megatron.util.Role;
import progi.megatron.validation.DonorValidator;
import progi.megatron.validation.IdValidator;
import progi.megatron.validation.OibValidator;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
    private final SecureTokenRepository secureTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecureTokenService secureTokenService;

    ;

    @Value("https://trueblood-be-dev.herokuapp.com/api/v1/donor/")
    private String baseURL;

    public DonorService(DonorRepository donorRepository, UserService userService, DonorValidator donorValidator, IdValidator idValidator, OibValidator oibValidator, PasswordEncoder passwordEncoder, SecureTokenRepository secureTokenRepository, ModelMapper modelMapper, SecureTokenRepository secureTokenRepository1) {
        this.donorRepository = donorRepository;
        this.userService = userService;
        this.donorValidator = donorValidator;
        this.idValidator = idValidator;
        this.oibValidator = oibValidator;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.secureTokenRepository = secureTokenRepository1;
    }

    java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

    public Donor createDonorByDonor(DonorByDonorDTOWithoutId donorByDonorDTOWithoutId) {
        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        user = userService.createUser(user);
        Donor donor = modelMapper.map(donorByDonorDTOWithoutId, Donor.class);
        donor.setDonorId(user.getUserId());

        donorValidator.validateDonor(donor);
        if (getDonorByOib(donor.getOib()) != null) {
            throw new WrongDonorException("Donor with that oib already exists. ");
        }
        donor = donorRepository.save(donor);


        //try {
        //    sendRegistrationConfirmationEmail(donor);
        //} catch (UnableToSendNotificationException e) {
        //    e.printStackTrace();
        //}
        logger.info("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return donor;
    }

    public Donor createDonorByBankWorker(DonorByBankWorkerDTOWithoutId donorByBankWorkerDTOWithoutId) {
        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        userService.createUser(user);
        Donor donor = modelMapper.map(donorByBankWorkerDTOWithoutId, Donor.class);
        donor.setDonorId(user.getUserId());

        donorValidator.validateDonor(donor);
        if (getDonorByOib(donor.getOib()) != null) {
            throw new WrongDonorException("Donor with that oib already exists. ");
        }
        donor = donorRepository.save(donor);

        //sendRegistrationConfirmationEmail(donor);
        logger.info("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return donor;
    }

    public Donor getDonorByOib(String oib) {
        oibValidator.validateOib(oib);
        return donorRepository.getDonorByOib(oib);
    }

    public Donor getDonorByDonorId(String donorId) {
        idValidator.validateId(donorId);
        return donorRepository.getDonorByDonorId(Long.valueOf(donorId));
    }

    public List<String> getOibsByFirstNameAndLastName(String firstName, String lastName) {
        List<Donor> donors = donorRepository.getDonorByFirstNameAndLastName(firstName, lastName);
        return donors.stream().map(donor -> donor.getOib()).collect(Collectors.toList());
    }

    // page numbering starts from 1
    public List<Donor> getDonorsAll(Integer resultsPerPage, Integer page) {
        List<Donor> donors = donorRepository.findAll();
        if (donors.size() < resultsPerPage) {
            return donors;
        }
        int startIndex = resultsPerPage * (page - 1);
        return donors.subList(startIndex, startIndex + resultsPerPage);
    }

    public List<Donor> getDonorsByAny(String query) {
        if(query.isEmpty())
            return new LinkedList<>();
        
        Set<Donor> donorSet = new HashSet<>();
        String[] querySplit = query.split(" ");
        boolean firstPass = true;
        for(String part : querySplit){
            Set<Donor> localDonorSet = new HashSet<>();
            try{
                Donor donorById = donorRepository.getDonorByDonorId(Long.valueOf(part));
                if(donorById != null) localDonorSet.add(donorById);
            } catch (NumberFormatException e){
            }
            localDonorSet.addAll(donorRepository.getDonorsByOibIsContaining(part));
            localDonorSet.addAll(donorRepository.getDonorByFirstNameIsContainingIgnoreCase(part));
            localDonorSet.addAll(donorRepository.getDonorByLastNameIsContainingIgnoreCase(part));
            if(firstPass) donorSet.addAll(localDonorSet);
            else donorSet.retainAll(localDonorSet);
            firstPass = false;
        }
        return donorSet.stream().collect(Collectors.toList());
    }

    public void sendRegistrationConfirmationEmail(Donor user) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user.getDonorId());
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Donor updateDonorByDonor(DonorByDonorDTOWithId donorNew) {
        Long donorId = donorNew.getDonorId();
        if (donorId == null) throw new WrongDonorException("Donor id is not given. ");
        Donor donor = donorRepository.getDonorByDonorId(donorId);
        if (donor == null) throw new WrongDonorException("There is no donor with that id.");
        String oibOld = donor.getOib();
        donor = modelMapper.map(donorNew, Donor.class);
        String oibNew = donor.getOib();
        donorValidator.validateDonor(donor);
        if (getDonorByOib(oibNew) != null && !oibNew.equals(oibOld)) {
            throw new WrongDonorException("Donor with that oib already exists. ");
        }
        donorRepository.save(donor);
        return donor;
    }

    public Donor updateDonorByBankWorker(Donor donorNew) {
        Long donorId = donorNew.getDonorId();
        if (donorId == null) throw new WrongDonorException("Donor id is not given. ");
        Donor donor = donorRepository.getDonorByDonorId(donorId);
        if (donor == null) throw new WrongDonorException("There is no donor with that id.");
        String oibOld = donor.getOib();
        donor = modelMapper.map(donorNew, Donor.class);
        String oibNew = donor.getOib();
        donorValidator.validateDonor(donor);
        if (getDonorByOib(oibNew) != null && !oibNew.equals(oibOld)) {
            throw new WrongDonorException("Donor with that oib already exists. "); //Je li ovo dobra poruka?
        }
        donorRepository.save(donor);
        return donor;
    }

}
