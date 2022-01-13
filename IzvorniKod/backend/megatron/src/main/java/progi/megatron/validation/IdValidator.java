package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongDonorException;

@Component
public class IdValidator {

    public boolean validateId(String id) {
        try {
            Long value = Long.valueOf(id);
        } catch (NumberFormatException ex) {
            throw new WrongDonorException("Id nije numerički.");
        }
        return true;
    }

}
