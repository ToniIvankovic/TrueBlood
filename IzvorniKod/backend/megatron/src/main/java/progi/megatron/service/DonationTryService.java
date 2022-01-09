package progi.megatron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progi.megatron.exception.DonationWaitingPeriodNotOver;
import progi.megatron.exception.WrongBankWorkerException;
import progi.megatron.exception.WrongDonationTryException;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.BankWorker;
import progi.megatron.model.DonationTry;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonationTryRequestDTO;
import progi.megatron.model.dto.DonationTryResponseDTO;
import progi.megatron.repository.DonationTryRepository;
import progi.megatron.validation.IdValidator;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationTryService {

    private final DonationTryRepository donationTryRepository;
    private final DonorService donorService;
    private final BankWorkerService bankWorkerService;
    private final BloodSupplyService bloodSupplyService;
    private final IdValidator idValidator;

    @Autowired
    private EmailService emailService;

    public DonationTryService(DonationTryRepository donationTryRepository, DonorService donorService, BankWorkerService bankWorkerService, BloodSupplyService bloodSupplyService, IdValidator idValidator) {
        this.donationTryRepository = donationTryRepository;
        this.donorService = donorService;
        this.bankWorkerService = bankWorkerService;
        this.bloodSupplyService = bloodSupplyService;
        this.idValidator = idValidator;
    }

    public DonationTryResponseDTO createDonationTry(DonationTryRequestDTO donationTryRequestDTO) {

        LocalDate lastDonationDate = getLastDonationDateForDonor(donationTryRequestDTO.getDonorId());
        if (lastDonationDate != null && lastDonationDate.plusMonths(3).isAfter(LocalDate.now())) {
            throw new DonationWaitingPeriodNotOver("Donor must wait at least three months after last donation before a new blood donation.");
        }

        boolean donated = false;

        Donor donor = donorService.getDonorByDonorId(donationTryRequestDTO.getDonorId());
        if (donor == null) throw new WrongDonorException("There is no donor with that id.");
        if (donor.getBloodType() == null) throw new WrongDonorException("Blood type for this donor is not defined.");

        BankWorker bankWorker = bankWorkerService.getBankWorkerByBankWorkerId(donationTryRequestDTO.getBankWorkerId());
        if (bankWorker == null) throw new WrongBankWorkerException("There is no bank worker with that id.");

        if (donationTryRequestDTO.getRejectReason() == null) {
            if (donor.getPermRejectedReason() != null) {
                donationTryRequestDTO.setRejectReason("Donor is permanently rejected.");
            } else {
                bloodSupplyService.manageBloodSupply(new String[]{donor.getBloodType()}, new int[]{1}, true);
                donated = true;
            }
        }

        if (donationTryRequestDTO.isReasonPerm()) {
            String permRejectReason = donationTryRequestDTO.getRejectReason();
            if (permRejectReason == null) throw new WrongDonationTryException("No reason for rejection given.");
            donor.setPermRejectedReason(permRejectReason);
            donorService.updateDonorByBankWorker(donor);
        }

        DonationTry donationTry = new DonationTry (
                null,
                donationTryRequestDTO.getRejectReason(),
                LocalDate.now(),
                donationTryRequestDTO.getDonationPlace(),
                donor,
                bankWorker
        );

        donationTry = donationTryRepository.save(donationTry);
        try {
            emailService.sendEmailWithAttachment(donor.getEmail(),"PDF Potvrda", "Poštovani, \n Potvrda se nalazi u prilogu. \n Srdačno,\n Vaš Trueblood", "templates/emails/pdf.html", donationTry);
        } catch (MessagingException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return new DonationTryResponseDTO(donationTry.getDonationId(), donated, donationTry.getRejectReason(), donationTry.getDonationDate(), donationTry.getDonationPlace(), donationTry.getDonor().getDonorId());
    }

    public List<DonationTryResponseDTO> getDonationTryHistory(String donorId) {
        idValidator.validateId(donorId);
        Donor donor = donorService.getDonorByDonorId(donorId);
        List<DonationTry> donationTries = donationTryRepository.getDonationTryByDonor(donor);
        List<DonationTryResponseDTO> donationTryResponseDTOS = new ArrayList<>();
        for (DonationTry donationTry : donationTries) {
            donationTryResponseDTOS.add(new DonationTryResponseDTO(donationTry.getDonationId(), donationTry.getRejectReason() == null, donationTry.getRejectReason(), donationTry.getDonationDate(), donationTry.getDonationPlace(), donationTry.getDonor().getDonorId()));
        }
        return donationTryResponseDTOS;
    }

    public DonationTry getDonationTryByDonationId(String donationId) {
        idValidator.validateId(donationId);
        DonationTry donationTry = donationTryRepository.getDonationTryByDonationId(Long.valueOf(donationId));
        return donationTry;
    }

    public void generatePDFCertificateForSuccessfulDonation(String donationId) {
        idValidator.validateId(donationId);
        DonationTry donationTry = getDonationTryByDonationId(donationId);
        if (donationTry != null && donationTry.getRejectReason() == null) {
            // todo: finish
        }
    }

    public List<Long> getIdsOfDonorsWhoDonatedToday() {
        return donationTryRepository.getDonationTryByDonationDate(LocalDate.now()).stream().map(donationTry -> donationTry.getDonor().getDonorId()).collect(Collectors.toList());
    }

    public List<Long> getIdsOfDonorsWhoseWaitingPeriodIsOver() {
        List<Donor> donors = donorService.getAllDonors();
        return donors.stream()
                    .filter(donor -> getWhenIsWaitingPeriodOverForDonor(String.valueOf(donor.getDonorId())) == 0)
                    .map(donor -> donor.getDonorId())
                    .collect(Collectors.toList());
    }

    public LocalDate getLastDonationDateForDonor(String donorId) {
        idValidator.validateId(donorId);
        List<DonationTryResponseDTO> donationTryHistory = getDonationTryHistory(donorId);
        LocalDate lastDonationTry = null;
        for (DonationTryResponseDTO donationTry : donationTryHistory) {
            if (donationTry.getRejectedReason() == null && (lastDonationTry == null || lastDonationTry.isBefore(donationTry.getDonationDate()))) {
                lastDonationTry = donationTry.getDonationDate();
            }
        }
        return lastDonationTry;
    }

    public long getWhenIsWaitingPeriodOverForDonor(String donorId) {
        idValidator.validateId(donorId);
        LocalDate lastDonationDate = getLastDonationDateForDonor(donorId);
        long daysUnitlWaitingPeriodOver;
        if (lastDonationDate == null) return 0;
        else {
            Donor donor = donorService.getDonorByDonorId(donorId);
            if (donor.getGender().equals("M")) daysUnitlWaitingPeriodOver = LocalDate.now().datesUntil(lastDonationDate.plusMonths(3)).count();
            else daysUnitlWaitingPeriodOver = LocalDate.now().datesUntil(lastDonationDate.plusMonths(4)).count();
        }
        return daysUnitlWaitingPeriodOver;
    }

}
