package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;

@Component
public class OibValidator {

    public boolean validateOib(String oib) {
        try {
            Long value = Long.valueOf(oib);
        } catch (NumberFormatException ex) {
            throw new WrongDonorException("Oib is not numeric. ");
        }
        if (oib.length() != 11) throw new WrongDonorException("Donor oib does not have exactly 11 characters. ");
        return true;
    }

}
