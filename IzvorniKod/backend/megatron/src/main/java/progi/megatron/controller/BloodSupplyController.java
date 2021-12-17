package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.dto.BloodSupplyDTO;
import progi.megatron.model.dto.DonationTryDTO;
import progi.megatron.service.BloodSupplyService;

@Controller
@RequestMapping("/api/v1/blood-supply")
public class BloodSupplyController {

    private BloodSupplyService bloodSupplyService;

    public BloodSupplyController(BloodSupplyService bloodSupplyService) {
        this.bloodSupplyService = bloodSupplyService;
    }

    @GetMapping("/{bloodType}")
    public ResponseEntity<Object> getBloodSupplyByBloodType(@PathVariable String bloodType) {
        try {
            return ResponseEntity.ok(bloodSupplyService.getBloodSupplyByBloodType(bloodType));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> getBloodSupply() {
        try {
            return ResponseEntity.ok(bloodSupplyService.getBloodSupply());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/decrease")
    public ResponseEntity<Object> decreaseBloodSupply(@RequestBody BloodSupplyDTO bloodSupplyDTO) {
        try {
            boolean decreased = bloodSupplyService.decreaseBloodSupply(bloodSupplyDTO.getBloodType(), bloodSupplyDTO.getNumberOfUnits());
            if (decreased) return ResponseEntity.ok("Successfully decreased blood supply.");
            else return ResponseEntity.ok("Could not decrease blood supply.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
