package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;

@Component
public class OibValidator {

    public boolean validateOib(String oib) {
        try {
            if(Long.valueOf(oib) < 0){
                throw new NumberFormatException();
            };
        } catch (NumberFormatException ex) {
            throw new WrongDonorException("Oib nije numerički.");
        }
        if (oib.length() != 11) throw new WrongDonorException("Oib mora imati točno 11 znakova.");
        return true;
    }

}
