package progi.megatron.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import progi.megatron.service.DonationTryService;

@Controller
@RequestMapping("/api/v1/donation-try")
public class DonationTryController {

    private final DonationTryService donationTryService;

    public DonationTryController(DonationTryService donationTryService) {
        this.donationTryService = donationTryService;
    }

    @Secured("ROLE_DONOR")
    @GetMapping
    public void donate() {

    }

}
