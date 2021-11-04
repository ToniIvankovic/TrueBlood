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

    private String lastDonationPlace;

//    @OneToOne(cascade = CascadeType.ALL, optional = false)
//    @PrimaryKeyJoinColumn
//    private User user;


//    /*@OneToMany
//    @JoinColumn(name = "donation_try_donor_id")
//    private DonationTry donationTry;*/
//
//    public void setDonorId(Long donorId) {
//        this.donorId = donorId;
//    }
//
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public void setOib(String oib) {
//        this.oib = oib;
//    }
//
//    public void setBirthDate(LocalDate birthDate) {
//        this.birthDate = birthDate;
//    }
//
//    public void setBirthPlace(String birthPlace) {
//        this.birthPlace = birthPlace;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public void setWorkPlace(String workPlace) {
//        this.workPlace = workPlace;
//    }
//
//    public void setPrivateContact(String privateContact) {
//        this.privateContact = privateContact;
//    }
//
//    public void setWorkContact(String workContact) {
//        this.workContact = workContact;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setBloodType(String bloodType) {
//        this.bloodType = bloodType;
//    }
//
//    public void setPermRejectedReason(String permRejectedReason) {
//        this.permRejectedReason = permRejectedReason;
//    }
//
//    public void setLastDonationLocation(String lastDonationLocation) {
//        this.lastDonationPlace = lastDonationLocation;
//    }
//
//    public void setDonationCount(int donationCount) {
//        this.donationCount = donationCount;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getOib() {
//        return oib;
//    }
//
//    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//
//    public String getBirthPlace() {
//        return birthPlace;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public String getWorkPlace() {
//        return workPlace;
//    }
//
//    public String getPrivateContact() {
//        return privateContact;
//    }
//
//    public String getWorkContact() {
//        return workContact;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getBloodType() {
//        return bloodType;
//    }
//
//    public String getPermRejectedReason() {
//        return permRejectedReason;
//    }
//
//    public String getLastDonationLocation() {
//        return lastDonationPlace;
//    }
//
//    public int getDonationCount() {
//        return donationCount;
//    }
//
//    public Donor(Long donorId, User user, String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace, String address, String workPlace, String privateContact, String workContact, String email, String bloodType, String permRejectedReason, String lastDonationPlace, int donationCount) {
//        this.donorId = donorId;
//        this.user = user;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.oib = oib;
//        this.birthDate = birthDate;
//        this.birthPlace = birthPlace;
//        this.address = address;
//        this.workPlace = workPlace;
//        this.privateContact = privateContact;
//        this.workContact = workContact;
//        this.email = email;
//        this.bloodType = bloodType;
//        this.permRejectedReason = permRejectedReason;
//        this.lastDonationPlace = lastDonationPlace;
//        this.donationCount = donationCount;
//    }
//
//    public Donor() {
//
//    }
//
//    public Donor(User user, String firstName, String lastName, String oib, LocalDate birthDate, String birthPlace, String address, String workPlace, String privateContact, String workContact, String email, String bloodType, String permRejectedReason, String lastDonationLocation, int donationCount) {
//        this.user = user;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.oib = oib;
//        this.birthDate = birthDate;
//        this.birthPlace = birthPlace;
//        this.address = address;
//        this.workPlace = workPlace;
//        this.privateContact = privateContact;
//        this.workContact = workContact;
//        this.email = email;
//        this.bloodType = bloodType;
//        this.permRejectedReason = permRejectedReason;
//        this.lastDonationPlace = lastDonationLocation;
//        this.donationCount = donationCount;
//    }

}
