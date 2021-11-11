package progi.megatron.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.Donor;
import progi.megatron.model.User;
import progi.megatron.model.dto.CreateDonorDTO;
import progi.megatron.service.DonorService;
import progi.megatron.service.UserService;
import progi.megatron.util.Role;

@RestController
@RequestMapping("/api/v1/donor")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping
    public void createDonor(@RequestBody Donor donor) {
        donorService.createDonor(donor);
        return;
    }

}
