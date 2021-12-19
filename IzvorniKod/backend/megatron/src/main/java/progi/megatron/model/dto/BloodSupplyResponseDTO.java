package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BloodSupplyResponseDTO {

    private String bloodType;
    private int numberOfUnits;
    private String review;
    private int maxUnits;
    private int minUnits;

    public BloodSupplyResponseDTO() {
    }

    public BloodSupplyResponseDTO(String bloodType, int numberOfUnits, String review, int maxUnits, int minUnits) {
        this.bloodType = bloodType;
        this.numberOfUnits = numberOfUnits;
        this.review = review;
        this.maxUnits = maxUnits;
        this.minUnits = minUnits;
    }

}
