package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;

import java.time.LocalDate;

@Component
public class DonorValidator {

    public final BloodSupplyValidator bloodSUpplyValidator;
    public final OibValidator oibValidator;

    public DonorValidator(BloodSupplyValidator bloodSUpplyValidator, OibValidator oibValidator) {
        this.bloodSUpplyValidator = bloodSUpplyValidator;
        this.oibValidator = oibValidator;
    }

    public boolean validateDonor(Donor donor) {
        if (oibValidator.validateOib(donor.getOib()) == false) return false;
        if (!donor.getEmail().contains("@")) throw new WrongDonorException("Donor email is not correct. ");
        if (donor.getBirthDate().plusYears(18).isAfter(LocalDate.now())) throw new WrongDonorException("Donor must be at least 18 years old. ");
        if (donor.getBloodType() != null) {
            bloodSUpplyValidator.validateBloodType(donor.getBloodType());
        }
        return true;
    }

}
