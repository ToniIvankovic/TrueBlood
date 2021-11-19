package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.Donor;

@Component
public class DonorValidator {

    public boolean validateDonor(Donor donor) {
        try {
            Long value = Long.valueOf(donor.getOib());
        } catch (Exception ex) {
            throw new WrongDonorException("Donor oib is not numeric. ");
        }
        if (donor.getOib().length() != 11) throw new WrongDonorException("Donor oib does not have exactly 11 characters. ");
        if (!donor.getEmail().contains("@")) throw new WrongDonorException("Donor email is not correct. ");
        // todo: rest of validation
        return true;
    }



}
