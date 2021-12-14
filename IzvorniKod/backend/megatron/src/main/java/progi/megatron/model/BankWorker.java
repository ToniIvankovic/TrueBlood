package progi.megatron.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    private LocalDate birthDate;

    private String birthPlace;

    private String address;

    private String workPlace;

    private String privateContact;

    private String workContact;

    private String email;

    public BankWorker() {
    }

    public BankWorker(Long bankWorkerId, String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace, String address, String workPlace, String privateContact, String workContact, String email) {
        this.bankWorkerId = bankWorkerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
        this.workPlace = workPlace;
        this.privateContact = privateContact;
        this.workContact = workContact;
        this.email = email;
    }

}
