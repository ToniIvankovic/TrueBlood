package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.exception.WrongBankWorkerException;
import progi.megatron.exception.WrongDonorException;
import progi.megatron.model.BankWorker;
import progi.megatron.model.DonationTry;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonationTryDTO;
import progi.megatron.repository.DonationTryRepository;

@Service
public class DonationTryService {

    private final DonationTryRepository donationTryRepository;
    private final DonorService donorService;
    private final BankWorkerService bankWorkerService;
    private final BloodSupplyService bloodSupplyService;

    public DonationTryService(DonationTryRepository donationTryRepository, DonorService donorService, BankWorkerService bankWorkerService, BloodSupplyService bloodSupplyService) {
        this.donationTryRepository = donationTryRepository;
        this.donorService = donorService;
        this.bankWorkerService = bankWorkerService;
        this.bloodSupplyService = bloodSupplyService;
    }

    public boolean createDonationTry(DonationTryDTO donationTryDTO){

        boolean donated = false;

        Donor donor = donorService.getDonorByDonorId(donationTryDTO.getDonorId());
        if (donor == null) throw new WrongDonorException("There is no donor with that id.");

        BankWorker bankWorker = bankWorkerService.getBankWorkerByBankWorkerId(donationTryDTO.getBankWorkerId());
        if (bankWorker == null) throw new WrongBankWorkerException("There is no bank worker with that id.");

        if (donationTryDTO.getRejectReason() == null) {
            if (donor.getPermRejectedReason() != null) {
                donationTryDTO.setRejectReason("Donor is permanently rejected.");
            } else {
                bloodSupplyService.donateBlood(donor.getBloodType());
                donated = true;
            }
        }

        DonationTry donationTry = new DonationTry (
                null,
                donationTryDTO.getRejectReason(),
                donor,
                bankWorker
        );

        donationTry = donationTryRepository.save(donationTry);

        return donated;
    }

}
