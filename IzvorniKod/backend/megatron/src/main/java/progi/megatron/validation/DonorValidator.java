package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;
import progi.megatron.service.BloodSupplyService;

import java.time.LocalDate;
import java.util.List;

@Component
public class DonorValidator {

    public final BloodSupplyValidator bloodSupplyValidator;
    public final OibValidator oibValidator;
    public final List<String> bloodTypes = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");

    public DonorValidator(BloodSupplyValidator bloodSupplyValidator, OibValidator oibValidator) {
        this.bloodSupplyValidator = bloodSupplyValidator;
        this.oibValidator = oibValidator;
    }

    public boolean validateDonor(Donor donor) {
        if (oibValidator.validateOib(donor.getOib()) == false) return false;
        if (!donor.getEmail().contains("@")) throw new WrongDonorException("Email nije validan.");
        String gender = donor.getGender();
        if (gender.length() != 1) throw new WrongDonorException("Spol nije validan.");
        if (gender.equals("M") && gender.equals("F")) throw new WrongDonorException("Spol nije validan.");
        if (donor.getBirthDate().plusYears(18).isAfter(LocalDate.now())) throw new WrongDonorException("Darivatelj krvi mora biti punoljetan.");
        if (donor.getBloodType() != null) {
            bloodSupplyValidator.validateBloodType(donor.getBloodType(), bloodTypes);
        }
        return true;
    }

}
