package progi.megatron.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import progi.megatron.exception.WrongBankWorkerException;
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
    private final ModelMapper modelMapper;

    public BankWorkerService(BankWorkerRepository bankWorkerRepository, UserService userService, PasswordEncoder passwordEncoder, IdValidator idValidator, OibValidator oibValidator, BankWorkerValidator bankWorkerValidator, ModelMapper modelMapper) {
        this.bankWorkerRepository = bankWorkerRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.idValidator = idValidator;
        this.oibValidator = oibValidator;
        this.bankWorkerValidator = bankWorkerValidator;
        this.modelMapper = modelMapper;
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
        BankWorker bankWorker = modelMapper.map(bankWorkerDTO, BankWorker.class);
        bankWorker.setBankWorkerId(user.getUserId());

        bankWorkerValidator.validateBankWorker(bankWorker);

        if (getBankWorkerByOib(bankWorker.getOib()) != null) {
            throw new WrongDonorException("Bank worker with that oib already exists. ");
        }

        // todo: send email
        logger.info("Sending e-mail to user. ID is " + user.getUserId() + ", password is " + password);

        return bankWorkerRepository.save(bankWorker);
    }

    public BankWorker updateBankWorkerByBankWorker(BankWorker bankWorkerNew) {
        Long bankWorkerId = bankWorkerNew.getBankWorkerId();
        if (bankWorkerId == null) throw new WrongBankWorkerException("Bank worker id is not given.");
        BankWorker bankWorker = bankWorkerRepository.getBankWorkerByBankWorkerId(bankWorkerId);
        if (bankWorker == null) throw new WrongBankWorkerException("There is no bank worker with that id.");
        String oibOld = bankWorker.getOib();
        bankWorker = modelMapper.map(bankWorkerNew, BankWorker.class);
        String oibNew = bankWorker.getOib();
        bankWorkerValidator.validateBankWorker(bankWorker);
        if (getBankWorkerByOib(oibNew) != null && !oibNew.equals(oibOld)) {
            throw new WrongBankWorkerException("Bank worker with that oib already exists.");
        }
        bankWorkerRepository.save(bankWorker);
        return bankWorker;
    }
}
