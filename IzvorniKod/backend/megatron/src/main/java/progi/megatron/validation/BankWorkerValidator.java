package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.BankWorker;

@Component
public class BankWorkerValidator {

    public final OibValidator oibValidator;

    public BankWorkerValidator(OibValidator oibValidator) {
        this.oibValidator = oibValidator;
    }

    public boolean validateBankWorker(BankWorker bankWorker) {
        if (oibValidator.validateOib(bankWorker.getOib()) == false) return false;
        if (!bankWorker.getEmail().contains("@")) throw new WrongDonorException("Donor email is not correct. ");
        return true;
    }

}
