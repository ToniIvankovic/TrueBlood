package progi.megatron.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "donor")
public class Donor implements Serializable {

    @Id
    private Long donorId;

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

    public Donor() {
    }

    public Donor(User user, String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace,
                 String address, String workPlace, String privateContact, String workContact, String email) {
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
    }


    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setPermRejectedReason(String permRejectedReason) {
        this.permRejectedReason = permRejectedReason;
    }

    public Long getDonorId() {
        return donorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getPrivateContact() {
        return privateContact;
    }

    public void setPrivateContact(String privateContact) {
        this.privateContact = privateContact;
    }

    public String getWorkContact() {
        return workContact;
    }

    public void setWorkContact(String workContact) {
        this.workContact = workContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getPermRejectedReason() {
        return permRejectedReason;
    }
}