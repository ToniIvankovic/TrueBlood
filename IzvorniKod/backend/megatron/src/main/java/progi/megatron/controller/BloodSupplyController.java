package progi.megatron.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import progi.megatron.model.dto.BloodSupplyDecreaseDTO;
import progi.megatron.model.dto.BloodSupplyRequestDTO;
import progi.megatron.service.BloodSupplyService;

@RestController
@CrossOrigin
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

    @GetMapping
    public ResponseEntity<Object> getBloodSupply() {
        try {
            return ResponseEntity.ok(bloodSupplyService.getBloodSupply());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<Object> setMinMax(@RequestBody BloodSupplyRequestDTO bloodSupplyRequestDTO) {
        try {
            return ResponseEntity.ok(bloodSupplyService.setMinMax(bloodSupplyRequestDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @Secured({"ROLE_BANK_WORKER"})
    @PostMapping("/decrease")
    public ResponseEntity<Object> decreaseBloodSupply(@RequestBody BloodSupplyDecreaseDTO bloodSupplyDecreaseDTO) {
        try {
            return ResponseEntity.ok(bloodSupplyService.manageBloodSupply(bloodSupplyDecreaseDTO.getBloodTypes(), bloodSupplyDecreaseDTO.getNumbersOfUnits(), false));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
}
