package progi.megatron.validation;


import org.springframework.stereotype.Component;
import progi.megatron.exception.WrongBloodTypeException;
import progi.megatron.exception.WrongDonorException;

import java.util.List;

@Component
public class BloodTypeValidator {


    public boolean validateBloodType(String bloodType) {
        List<String> list = List.of("A+", "A-", "B+", "B-", "0+", "0-", "AB+", "AB-");
        if (!list.contains(bloodType)) throw new WrongBloodTypeException("Blood type does not exist, existing bloodtypes are: A+, A-, B+, B-, 0+, 0-, AB+, AB-");
        return true;
    }

}
