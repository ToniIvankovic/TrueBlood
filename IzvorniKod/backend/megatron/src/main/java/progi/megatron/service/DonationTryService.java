package progi.megatron.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progi.megatron.exception.WrongBankWorkerException;
import progi.megatron.exception.WrongDonationTryException;
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

    @Transactional
    public boolean createDonationTry(DonationTryDTO donationTryDTO){

        boolean donated = false;

        String bloodType = donationTryDTO.getBloodType();

        Donor donor = donorService.getDonorByDonorId(donationTryDTO.getDonorId());
        if (donor == null) throw new WrongDonorException("There is no donor with that id.");

        BankWorker bankWorker = bankWorkerService.getBankWorkerByBankWorkerId(donationTryDTO.getBankWorkerId());
        if (bankWorker == null) throw new WrongBankWorkerException("There is no bank worker with that id.");

        if (!bloodType.trim().equals(donor.getBloodType().trim())) throw new WrongDonationTryException("Given blood type does not match donor's blood type.");

        if (donationTryDTO.getRejectReason() == null) {
            if (donor.getPermRejectedReason() == null) {
                donationTryDTO.setRejectReason("Donor is permanently rejected.");
            } else {
                bloodSupplyService.donateBlood(bloodType);
                donated = true;
            }
        }

        DonationTry donationTry = new DonationTry (
                null,
                donationTryDTO.getRejectReason(),
                bloodType,
                donor,
                bankWorker
        );

        donationTry = donationTryRepository.save(donationTry);

        return donated;
    }

}
