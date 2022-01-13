package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.email.AccountVerificationEmailContext;
import progi.megatron.exception.WrongBankWorkerException;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.BankWorker;
import progi.megatron.model.SecureToken;
import progi.megatron.model.User;
import progi.megatron.model.dto.BankWorkerDTO;
import progi.megatron.repository.BankWorkerRepository;
import progi.megatron.repository.SecureTokenRepository;
import progi.megatron.util.Role;
import progi.megatron.validation.BankWorkerValidator;
import progi.megatron.validation.IdValidator;
import progi.megatron.validation.OibValidator;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BankWorkerService {

    @Value("http://trueblood-be-dev.herokuapp.com/api/v1/bank-worker/")
    private String baseURL;

    private final BankWorkerRepository bankWorkerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IdValidator idValidator;
    private final OibValidator oibValidator;
    private final BankWorkerValidator bankWorkerValidator;
    private final ModelMapper modelMapper;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailService emailService;
    private final SecureTokenService secureTokenService;

    public BankWorkerService(BankWorkerRepository bankWorkerRepository, UserService userService, PasswordEncoder passwordEncoder, IdValidator idValidator, OibValidator oibValidator, BankWorkerValidator bankWorkerValidator, ModelMapper modelMapper, SecureTokenRepository secureTokenRepository, EmailService emailService, SecureTokenService secureTokenService) {
        this.bankWorkerRepository = bankWorkerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.idValidator = idValidator;
        this.oibValidator = oibValidator;
        this.bankWorkerValidator = bankWorkerValidator;
        this.modelMapper = modelMapper;
        this.secureTokenRepository = secureTokenRepository;
        this.emailService = emailService;
        this.secureTokenService = secureTokenService;
    }

    public List<BankWorker> getAllBankWorkers() {
        return bankWorkerRepository.findAll();
    }

    public BankWorker getBankWorkerByBankWorkerId(String bankWorkerId) {
        idValidator.validateId(bankWorkerId);
        return bankWorkerRepository.getBankWorkerByBankWorkerId(Long.valueOf(bankWorkerId));
    }

    public BankWorker getBankWorkerByOib(String oib){
        oibValidator.validateOib(oib);
        return bankWorkerRepository.getNotDeactivatedBankWorkerByOib(oib);
    }

    public List<BankWorker> getBankWorkersByAny(String query) {
        if (query.isEmpty()) return new LinkedList<>();

        Set<BankWorker> bankWorkerSet = new HashSet<>();
        String[] querySplit = query.split(" ");
        boolean firstPass = true;
        for (String part : querySplit) {
            Set<BankWorker> localBankWorkerSet = new HashSet<>();
            try {
                BankWorker bankWorkerById = bankWorkerRepository.getBankWorkerByBankWorkerId(Long.valueOf(part));
                if(bankWorkerById != null) localBankWorkerSet.add(bankWorkerById);
            } catch (NumberFormatException e){
            }
            localBankWorkerSet.addAll(bankWorkerRepository.getBankWorkersByOibIsContaining(part));
            localBankWorkerSet.addAll(bankWorkerRepository.getBankWorkerByFirstNameIsContainingIgnoreCase(part));
            localBankWorkerSet.addAll(bankWorkerRepository.getBankWorkerByLastNameIsContainingIgnoreCase(part));
            if (firstPass) bankWorkerSet.addAll(localBankWorkerSet);
            else bankWorkerSet.retainAll(localBankWorkerSet);
            firstPass = false;
        }
        return bankWorkerSet.stream().collect(Collectors.toList());
    }

    public BankWorker createBankWorker(BankWorkerDTO bankWorkerDTO) {
        bankWorkerValidator.validateBankWorker(modelMapper.map(bankWorkerDTO, BankWorker.class));

        String password = userService.randomPassword();
        User user = new User(Role.BANK_WORKER, passwordEncoder.encode(password));
        user = userService.createUser(user);
        BankWorker bankWorker = modelMapper.map(bankWorkerDTO, BankWorker.class);
        bankWorker.setBankWorkerId(user.getUserId());

        bankWorkerValidator.validateBankWorker(bankWorker);

        if (getBankWorkerByOib(bankWorker.getOib()) != null) {
            throw new WrongDonorException("Već postoji djelatnik banke krvi s tim oibom.");
        }

        try {
            sendRegistrationConfirmationEmail(bankWorker, user.getUserId(), password);
        } catch (UnableToSendNotificationException e){
            e.printStackTrace();
        }

        System.out.println("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return bankWorkerRepository.save(bankWorker);
    }
    public void sendRegistrationConfirmationEmail(BankWorker user, Long id, String password) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user.getBankWorkerId());
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.initBankWorker(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext, id, password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public BankWorker updateBankWorkerByBankWorker(BankWorker bankWorkerNew) {
        Long bankWorkerId = bankWorkerNew.getBankWorkerId();
        if (bankWorkerId == null) throw new WrongBankWorkerException("Id djelatnika banke nije definiran.");
        BankWorker bankWorker = bankWorkerRepository.getBankWorkerByBankWorkerId(bankWorkerId);
        if (bankWorker == null) throw new WrongBankWorkerException("Ne postoji djelatnik banke krvi s tim id-em.");
        String oibOld = bankWorker.getOib();
        bankWorker = modelMapper.map(bankWorkerNew, BankWorker.class);
        String oibNew = bankWorker.getOib();
        bankWorkerValidator.validateBankWorker(bankWorker);
        if (getBankWorkerByOib(oibNew) != null && !oibNew.equals(oibOld)) {
            throw new WrongBankWorkerException("Već postoji djelatnik banke krvi s tim id-em.");
        }
        bankWorkerRepository.save(bankWorker);
        return bankWorker;
    }

}
