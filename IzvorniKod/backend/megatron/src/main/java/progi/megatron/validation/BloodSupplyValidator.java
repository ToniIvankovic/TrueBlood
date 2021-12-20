package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongBloodSupplyException;
import progi.megatron.model.BloodSupply;
import java.util.List;

@Component
public class BloodSupplyValidator {

    public boolean validateBloodType(String bloodType) {
        List<String> list = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");
        if (!list.contains(bloodType.trim())) throw new WrongBloodSupplyException("Blood type does not exist, existing bloodtypes are: A+, A-, B+, B-, 0+, 0-, AB+, AB-");
        return true;
    }

    public boolean validateBloodSupply(BloodSupply bloodSupply) {
        if (bloodSupply.getMaxUnits() < bloodSupply.getMinUnits()) {
            throw new WrongBloodSupplyException("Incorrect blood supply min and max.");
        }
        return true;
    }

}
