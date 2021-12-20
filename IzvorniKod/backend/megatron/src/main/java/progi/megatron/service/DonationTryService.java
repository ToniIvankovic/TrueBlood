package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.exception.WrongBankWorkerException;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.BankWorker;
import progi.megatron.model.DonationTry;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonationTryRequestDTO;
import progi.megatron.model.dto.DonationTryResponseDTO;
import progi.megatron.repository.DonationTryRepository;
import progi.megatron.validation.IdValidator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DonationTryService {

    private final DonationTryRepository donationTryRepository;
    private final DonorService donorService;
    private final BankWorkerService bankWorkerService;
    private final BloodSupplyService bloodSupplyService;
    private final IdValidator idValidator;

    public DonationTryService(DonationTryRepository donationTryRepository, DonorService donorService, BankWorkerService bankWorkerService, BloodSupplyService bloodSupplyService, IdValidator idValidator) {
        this.donationTryRepository = donationTryRepository;
        this.donorService = donorService;
        this.bankWorkerService = bankWorkerService;
        this.bloodSupplyService = bloodSupplyService;
        this.idValidator = idValidator;
    }

    public DonationTryResponseDTO createDonationTry(DonationTryRequestDTO donationTryRequestDTO){
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
                bloodSupplyService.donateBlood(donor.getBloodType());
                donated = true;
            }
        }

        DonationTry donationTry = new DonationTry (
                null,
                donationTryRequestDTO.getRejectReason(),
                LocalDate.now(),
                donationTryRequestDTO.getDonationPlace(),
                donor,
                bankWorker
        );
        if (!donated) donationTry.setRejectReason("Donor is permanently rejected.");

        donationTry = donationTryRepository.save(donationTry);

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

    public void getDonationTryByDonationId(String donationId) {
        idValidator.validateId(donationId);
        DonationTry donationTry = donationTryRepository.getDonationTryByDonationId(Long.valueOf(donationId));
        if (donationTry.getRejectReason() == null) {
            // todo: download certificate
        }
    }

}
