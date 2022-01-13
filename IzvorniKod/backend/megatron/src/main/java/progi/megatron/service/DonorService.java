package progi.megatron.service;

import org.modelmapper.ModelMapper;
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

    @Value("http://trueblood-be-dev.herokuapp.com/api/v1/donor/")
    private String baseURL;

    private final DonorRepository donorRepository;
    private final UserService userService;
    private final DonorValidator donorValidator;
    private final IdValidator idValidator;
    private final OibValidator oibValidator;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailService emailService;
    private final SecureTokenService secureTokenService;

    public DonorService(DonorRepository donorRepository, UserService userService, DonorValidator donorValidator, IdValidator idValidator, OibValidator oibValidator, PasswordEncoder passwordEncoder, ModelMapper modelMapper, SecureTokenRepository secureTokenRepository, EmailService emailService, SecureTokenService secureTokenService) {
        this.donorRepository = donorRepository;
        this.userService = userService;
        this.donorValidator = donorValidator;
        this.idValidator = idValidator;
        this.oibValidator = oibValidator;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.secureTokenRepository = secureTokenRepository;
        this.emailService = emailService;
        this.secureTokenService = secureTokenService;
    }

    public Donor createDonorByDonor(DonorByDonorDTOWithoutId donorByDonorDTOWithoutId) {
        donorValidator.validateDonor(modelMapper.map(donorByDonorDTOWithoutId, Donor.class));

        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        user = userService.createUser(user);
        Donor donor = modelMapper.map(donorByDonorDTOWithoutId, Donor.class);
        donor.setDonorId(user.getUserId());

        if (getAllDonorByOib(donor.getOib()) != null) {
            throw new WrongDonorException("Već postoji darivatelj krvi s tim id-em.");
        }
        donor = donorRepository.save(donor);
        
        try {
            sendRegistrationConfirmationEmail(donor,user.getUserId(), password);
        }catch (UnableToSendNotificationException e){
            e.printStackTrace();
        }
        System.out.println("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return donor;
    }

    public Donor createDonorByBankWorker(DonorByBankWorkerDTOWithoutId donorByBankWorkerDTOWithoutId) {
        donorValidator.validateDonor(modelMapper.map(donorByBankWorkerDTOWithoutId, Donor.class));

        String password = userService.randomPassword();
        User user = new User(Role.DONOR, passwordEncoder.encode(password));
        userService.createUser(user);
        Donor donor = modelMapper.map(donorByBankWorkerDTOWithoutId, Donor.class);
        donor.setDonorId(user.getUserId());

        if (getAllDonorByOib(donor.getOib()) != null) {
            throw new WrongDonorException("Već postoji darivatelj krvi s tim id-em.");
        }
        donor = donorRepository.save(donor);

        try {
            sendRegistrationConfirmationEmail(donor, user.getUserId(), password);
        }catch (UnableToSendNotificationException e){
            e.printStackTrace();
        }

        System.out.println("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return donor;
    }

    private Donor getAllDonorByOib(String oib) {
        oibValidator.validateOib(oib);
        return donorRepository.getDonorByOib(oib);
    }

    public Donor getNotDeactivatedDonorByOib(String oib) {
        oibValidator.validateOib(oib);
        return donorRepository.getNotDeactivatedDonorByOib(oib);
    }

    public Donor getDonorByDonorId(String donorId) {
        idValidator.validateId(donorId);
        return donorRepository.getNotDeactivatedDonorByDonorId(Long.valueOf(donorId));
    }

    public List<String> getOibsByFirstNameAndLastName(String firstName, String lastName) {
        List<Donor> donors = donorRepository.getNotDeactivatedDonorByFirstNameAndLastName(firstName, lastName);
        return donors.stream().map(donor -> donor.getOib()).collect(Collectors.toList());
    }

    // page numbering starts from 1
    public List<Donor> getDonorsAll(Integer resultsPerPage, Integer page) {
        List<Donor> donors = donorRepository.getAllNotDeactivatedDonors();
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
                Donor donorById = donorRepository.getNotDeactivatedDonorByDonorId(Long.valueOf(part));
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

    public void sendRegistrationConfirmationEmail(Donor user, Long id, String password) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user.getDonorId());
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext, id, password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendCanDonateAgain(Donor donor){
        try {
            System.out.println("Gonna try to send mail now ");
            emailService.sendNotificationEmail(donor.getEmail(),"Ponovo možeš donirati", donor.getFirstName());
        } catch (MessagingException e){
            System.out.println("Didnt manage to send mail");
            e.printStackTrace();
        }

    }

    public Donor updateDonorByDonor(DonorByDonorDTOWithId donorNew) {
        Long donorId = donorNew.getDonorId();
        if (donorId == null) throw new WrongDonorException("Id darivatelja krvi nije definiran.");
        Donor donor = donorRepository.getNotDeactivatedDonorByDonorId(donorId);
        if (donor == null) throw new WrongDonorException("Ne postoji darivatelj krvi s tim id-em.");
        String oibOld = donor.getOib();
        donor = modelMapper.map(donorNew, Donor.class);
        String oibNew = donor.getOib();
        donorValidator.validateDonor(donor);
        if (getAllDonorByOib(oibNew) != null && !oibNew.equals(oibOld)) {
            throw new WrongDonorException("Nije moguće promjeniti oib darivatelja krvi jer već postoji darivatelj krvi s tim oibom.");
        }
        donorRepository.save(donor);
        return donor;
    }

    public Donor updateDonorByBankWorker(Donor donorNew) {
        Long donorId = donorNew.getDonorId();
        if (donorId == null) throw new WrongDonorException("Id darivatelja krvi nije definiran.");
        Donor donor = donorRepository.getNotDeactivatedDonorByDonorId(donorId);
        if (donor == null) throw new WrongDonorException("Ne postoji darivatelj krvi s tim id-em.");
        String oibOld = donor.getOib();
        donor = modelMapper.map(donorNew, Donor.class);
        String oibNew = donor.getOib();
        donorValidator.validateDonor(donor);
        if (getAllDonorByOib(oibNew) != null && !oibNew.equals(oibOld)) {
            throw new WrongDonorException("Nije moguće promjeniti oib darivatelja krvi jer već postoji darivatelj krvi s tim oibom.");
        }
        donorRepository.save(donor);
        return donor;
    }

    public List<Donor> getAllDonors() {
        return donorRepository.getAllNotDeactivatedDonors();
    }

    public List<Donor> getAllDonorsWithBloodType(String bloodType){ return donorRepository.getDonorsByBloodType(bloodType);}

}
