package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import progi.megatron.model.BloodSupply;

@Getter
@Setter
@Component
public class BloodSupplyDTO {

    private String bloodType;
    private int numberOfUnits;

    public BloodSupplyDTO() {
    }

    public BloodSupplyDTO(String bloodType, int numberOfUnits) {
        this.bloodType = bloodType;
        this.numberOfUnits = numberOfUnits;
    }

}
