package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import progi.megatron.model.BankWorker;
import java.time.LocalDate;

@Getter
@Setter
@Component
public class BankWorkerDTO {

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

    public BankWorkerDTO() {
    }

    public BankWorkerDTO(String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace, String address, String workPlace, String privateContact, String workContact, String email) {
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

    public BankWorker bankWorkerDTOToBankWorker(BankWorkerDTO bankWorkerDTO, Long userId) {
        return new BankWorker(userId, bankWorkerDTO.firstName, bankWorkerDTO.lastName, bankWorkerDTO.oib,
                bankWorkerDTO.birthDate, bankWorkerDTO.birthPlace, bankWorkerDTO.address, bankWorkerDTO.workPlace,
                bankWorkerDTO.privateContact, bankWorkerDTO.workContact, bankWorkerDTO.email);
    }

}
