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
        if (!bankWorker.getEmail().contains("@")) throw new WrongDonorException("Email nije validan.");
        if (bankWorker.getBirthDate().plusYears(18).isAfter(LocalDate.now())) throw new WrongDonorException("Djelatnik banke mora biti punoljetan.");
        return true;
    }

}
