package progi.megatron.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "blood_supply")
public class BloodSupply {

    @Id
    private String bloodType;

    private int numberOfUnits;

    private int maxUnits;

    private int minUnits;

}
