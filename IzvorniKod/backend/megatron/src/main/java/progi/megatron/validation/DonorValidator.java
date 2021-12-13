package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;

@Component
public class DonorValidator {

    public boolean validateDonor(Donor donor) {
        if (validateOib(donor.getOib()) == false) return false;
        if (!donor.getEmail().contains("@")) throw new WrongDonorException("Donor email is not correct. ");
        // todo: rest of validation
        return true;
    }

    public boolean validateOib(String oib) {
        try {
            Long value = Long.valueOf(oib);
        } catch (Exception ex) {
            throw new WrongDonorException("Donor oib is not numeric. ");
        }
        if (oib.length() != 11) throw new WrongDonorException("Donor oib does not have exactly 11 characters. ");
        return true;
    }

}
