package progi.megatron.service;

import org.springframework.stereotype.Service;
import progi.megatron.repository.DonationTryRepository;

@Service
public class DonationTryService {

    private final DonationTryRepository donationTryRepository;

    public DonationTryService(DonationTryRepository donationTryRepository) {
        this.donationTryRepository = donationTryRepository;
    }

}
