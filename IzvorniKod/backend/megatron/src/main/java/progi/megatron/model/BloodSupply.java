package progi.megatron.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blood_supply")
public class BloodSupply {

    @Id
    private String bloodType;

    private int numberOfUnits;

    private int maxUnits;

    private int minUnits;

}
