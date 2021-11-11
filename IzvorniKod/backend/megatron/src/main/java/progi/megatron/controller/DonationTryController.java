package progi.megatron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import progi.megatron.service.DonationTryService;

@Controller
@RequestMapping("/api/v1/donation-try")
public class DonationTryController {

    private final DonationTryService donationTryService;

    public DonationTryController(DonationTryService donationTryService) {
        this.donationTryService = donationTryService;
    }

}
