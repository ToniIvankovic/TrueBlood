package progi.megatron.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import progi.megatron.service.BloodSupplyService;

@Controller
@RequestMapping("/api/v1/blood-supply")
public class BloodSupplyController {

    private BloodSupplyService bloodSupplyService;

    public BloodSupplyController(BloodSupplyService bloodSupplyService) {
        this.bloodSupplyService = bloodSupplyService;
    }

}
