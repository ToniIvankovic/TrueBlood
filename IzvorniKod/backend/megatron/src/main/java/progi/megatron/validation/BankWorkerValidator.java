package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.BankWorker;

import java.time.LocalDate;

@Component
public class BankWorkerValidator {

    public final OibValidator oibValidator;

    public BankWorkerValidator(OibValidator oibValidator) {
        this.oibValidator = oibValidator;
    }

    public boolean validateBankWorker(BankWorker bankWorker) {
        if (oibValidator.validateOib(bankWorker.getOib()) == false) return false;
        if (!bankWorker.getEmail().contains("@")) throw new WrongDonorException("Donor email is not correct. ");
        if (bankWorker.getBirthDate().plusYears(18).isAfter(LocalDate.now())) throw new WrongDonorException("Bank worker must be at least 18 years old. ");
        return true;
    }

}
