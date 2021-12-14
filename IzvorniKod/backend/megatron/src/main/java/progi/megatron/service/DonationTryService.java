package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.model.DonationTry;
import progi.megatron.model.Donor;
import progi.megatron.model.dto.DonationTryDTO1;
import progi.megatron.model.dto.DonationTryDTO2;
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

    public DonationTry createDonationTry(DonationTryDTO1 donationTryDTO1){

        DonationTryDTO2 donationTryDTO2 = new DonationTryDTO2(
                donationTryDTO1.getBloodType(),
                donationTryDTO1.getRejectReason(),
                donorService.getDonorByDonorId(donationTryDTO1.getDonorId()),
                bankWorkerService.
        )

        if (donationTry.getRejectReason() == null) {
            Donor donor = donorService.getDonorByDonorId(String.valueOf(donationTry.getDonor().getDonorId()));
            if (donor.getPermRejectedReason() != null) {
                donationTry.setRejectReason("Donor is permanently rejected.");
            } else {
                bloodSupplyService.donateBlood(donor.getBloodType());
            }
        }
        return donationTryRepository.save(donationTry);
    }

}
