package progi.megatron.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BloodSupplyResponseDTO {

    private String bloodType;
    private int numberOfUnits;
    private String review;
    private int maxUnits;
    private int minUnits;

}
