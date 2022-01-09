package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;
import progi.megatron.service.BloodSupplyService;

import java.time.LocalDate;

@Component
public class DonorValidator {

    public final BloodSupplyService bloodSupplyService;
    public final BloodSupplyValidator bloodSupplyValidator;
    public final OibValidator oibValidator;

    public DonorValidator(BloodSupplyService bloodSupplyService, BloodSupplyValidator bloodSupplyValidator, OibValidator oibValidator) {
        this.bloodSupplyService = bloodSupplyService;
        this.bloodSupplyValidator = bloodSupplyValidator;
        this.oibValidator = oibValidator;
    }

    public boolean validateDonor(Donor donor) {
        if (oibValidator.validateOib(donor.getOib()) == false) return false;
        if (!donor.getEmail().contains("@")) throw new WrongDonorException("Donor email is not correct. ");
        String gender = donor.getGender();
        if (gender.length() != 1) throw new WrongDonorException("Donor gender is invalid. ");
        if (gender.equals("M") && gender.equals("F")) throw new WrongDonorException("Donor gender is invalid. ");
        if (donor.getBirthDate().plusYears(18).isAfter(LocalDate.now())) throw new WrongDonorException("Donor must be at least 18 years old. ");
        if (donor.getBloodType() != null) {
            bloodSupplyValidator.validateBloodType(donor.getBloodType(), bloodSupplyService.bloodTypes);
        }
        return true;
    }

}
