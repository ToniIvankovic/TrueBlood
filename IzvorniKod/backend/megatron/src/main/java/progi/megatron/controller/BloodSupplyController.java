package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import progi.megatron.service.BloodSupplyService;

@Controller
@RequestMapping("/api/v1/blood-supply")
public class BloodSupplyController {

    private BloodSupplyService bloodSupplyService;

    public BloodSupplyController(BloodSupplyService bloodSupplyService) {
        this.bloodSupplyService = bloodSupplyService;
    }

    @GetMapping("/{bloodType}")
    public ResponseEntity<Object> getBloodsupplyByBloodType(@PathVariable String bloodType) {
        try {
            return ResponseEntity.ok(bloodSupplyService.getBloodsupplyByBloodType(bloodType));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


}
