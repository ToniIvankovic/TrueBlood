package progi.megatron.validation;

import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongBloodSupplyException;
import progi.megatron.model.BloodSupply;
import java.util.List;

@Component
public class BloodSupplyValidator {

    public boolean validateBloodType(String bloodType, List<String> bloodTypes) {
        if (!bloodTypes.contains(bloodType.trim())) throw new WrongBloodSupplyException("Krvna grupa nije validna, validne krvne grupe su: A+, A-, B+, B-, 0+, 0-, AB+, AB-");
        return true;
    }

    public boolean validateBloodSupply(BloodSupply bloodSupply) {
        if (bloodSupply.getMaxUnits() < bloodSupply.getMinUnits()) {
            throw new WrongBloodSupplyException("Neispravno definirane granice količina krvi.");
        }
        return true;
    }

}
