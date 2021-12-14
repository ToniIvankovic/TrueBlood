package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;

@Component
public class DonorValidator {

    public final BloodTypeValidator bloodTypeValidator;
    public final OibValidator oibValidator;

    public DonorValidator(BloodTypeValidator bloodTypeValidator, OibValidator oibValidator) {
        this.bloodTypeValidator = bloodTypeValidator;
        this.oibValidator = oibValidator;
    }

    public boolean validateDonor(Donor donor) {
        if (oibValidator.validateOib(donor.getOib()) == false) return false;
        if (!donor.getEmail().contains("@")) throw new WrongDonorException("Donor email is not correct. ");
        if (donor.getBloodType() != null) {
            bloodTypeValidator.validateBloodType(donor.getBloodType());
        }
        return true;
    }

}
