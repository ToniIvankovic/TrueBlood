package progi.megatron.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.BankWorker;
import progi.megatron.model.User;
import progi.megatron.model.dto.BankWorkerDTO;
import progi.megatron.repository.BankWorkerRepository;
import progi.megatron.util.Role;
import progi.megatron.validation.BankWorkerValidator;
import progi.megatron.validation.IdValidator;
import progi.megatron.validation.OibValidator;

@Service
public class BankWorkerService {

    private final BankWorkerRepository bankWorkerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IdValidator idValidator;
    private final OibValidator oibValidator;
    private final BankWorkerValidator bankWorkerValidator;

    public BankWorkerService(BankWorkerRepository bankWorkerRepository, UserService userService, PasswordEncoder passwordEncoder, IdValidator idValidator, OibValidator oibValidator, BankWorkerValidator bankWorkerValidator) {
        this.bankWorkerRepository = bankWorkerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.idValidator = idValidator;
        this.oibValidator = oibValidator;
        this.bankWorkerValidator = bankWorkerValidator;
    }

    java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    public BankWorker getBankWorkerByBankWorkerId(String bankWorkerId) {
        idValidator.validateId(bankWorkerId);
        return bankWorkerRepository.getBankWorkerByBankWorkerId(Long.valueOf(bankWorkerId));
    }

    public BankWorker getBankWorkerByOib(String oib){
        oibValidator.validateOib(oib);
        return bankWorkerRepository.getBankWorkerByOib(oib);
    }

    public BankWorker createBankWorker(BankWorkerDTO bankWorkerDTO) {

        String password = userService.randomPassword();
        User user = new User(Role.BANK_WORKER, passwordEncoder.encode(password));
        user = userService.createUser(user);
        BankWorker bankWorker = bankWorkerDTO.bankWorkerDTOToBankWorker(bankWorkerDTO, user.getUserId());

        bankWorkerValidator.validateBankWorker(bankWorker);

        if (getBankWorkerByOib(bankWorker.getOib()) != null) {
            throw new WrongDonorException("Bank worker with that oib already exists. ");
        }

        // todo: send email
        logger.info("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return bankWorkerRepository.save(bankWorker);
    }

}
