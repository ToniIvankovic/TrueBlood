package progi.megatron.model;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bank_worker")
public class BankWorker implements Serializable {

    @Id
    private Long bankWorkerId;

    private String firstName;

    private String lastName;

    private String oib;

    private String birthDate;

    private String birthPlace;

    private String address;

    private String workPlace;

    private String privateContact;

    private String workContact;

    private String email;

}
