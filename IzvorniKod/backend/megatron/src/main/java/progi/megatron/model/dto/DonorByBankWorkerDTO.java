package progi.megatron.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import progi.megatron.model.Donor;
import java.time.LocalDate;

@Getter
@Setter
@Component
public class DonorByBankWorkerDTO {

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
    private String bloodType;
    private String permRejectedReason;


    public DonorByBankWorkerDTO() {}

    public DonorByBankWorkerDTO(String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace, String address, String workPlace, String privateContact, String workContact, String email, String bloodType, String permRejectedReason) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.address = address;
        this.workPlace = workPlace;
        this.workContact = workContact;
        this.privateContact = privateContact;
        this.email = email;
        this.bloodType = bloodType;
        this.permRejectedReason = permRejectedReason;
    }

    public Donor DonorByBankWorkerDTOToDonor(DonorByBankWorkerDTO donorByBankWorkerDTO, Long userId) {
        return new Donor(userId, donorByBankWorkerDTO.firstName, donorByBankWorkerDTO.lastName, donorByBankWorkerDTO.oib,
                donorByBankWorkerDTO.birthDate, donorByBankWorkerDTO.birthPlace, donorByBankWorkerDTO.address,
                donorByBankWorkerDTO.workPlace, donorByBankWorkerDTO.workContact, donorByBankWorkerDTO.privateContact,
                donorByBankWorkerDTO.email, donorByBankWorkerDTO.bloodType, donorByBankWorkerDTO.permRejectedReason);
    }
    
}
