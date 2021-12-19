package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BloodSupplyRequestDTO {

    private String bloodType;
    private int maxUnits;
    private int minUnits;

    public BloodSupplyRequestDTO() {
    }

    public BloodSupplyRequestDTO(String bloodType, int maxUnits, int minUnits) {
        this.bloodType = bloodType;
        this.maxUnits = maxUnits;
        this.minUnits = minUnits;
    }

}
