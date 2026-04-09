package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.PrescriptionDto;
import com.safwantech.hms_backend.security.CurrentUserUtil;
import com.safwantech.hms_backend.service.PrescriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final CurrentUserUtil currentUserUtil;

    @GetMapping("/health")
    public String healthCheck(){
        return "api is ok";
    }

    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(
            @Valid @RequestBody PrescriptionDto dto
    ) {
    try {
        dto.setClinicId(currentUserUtil.getCurrentClinicId());
        PrescriptionDto createdPrescription = prescriptionService.createPrescription(dto);

        return new ResponseEntity<>(createdPrescription, HttpStatus.CREATED);
    }catch(Exception e){
        e.printStackTrace();
        return ResponseEntity.badRequest().build();
    }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(
            @PathVariable @Positive Long id
    ) {
        PrescriptionDto prescription = prescriptionService.getPrescriptionById(currentUserUtil.getCurrentClinicId(), id);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDto>> getAllPrescriptions() {
        List<PrescriptionDto> prescriptions = prescriptionService.getAllPrescriptions(currentUserUtil.getCurrentClinicId());
        return ResponseEntity.ok(prescriptions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionDto> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionDto dto
    ) {
    try {
        dto.setClinicId(currentUserUtil.getCurrentClinicId());
        PrescriptionDto updatedPrescription =
                prescriptionService.updatePrescription(currentUserUtil.getCurrentClinicId(), id, dto);

        return ResponseEntity.ok(updatedPrescription);
    }catch(Exception e ){
        return ResponseEntity.badRequest().build();
    }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(
            @PathVariable Long id
    ) {
    try {
        prescriptionService.deletePrescription(currentUserUtil.getCurrentClinicId(), id);

        return ResponseEntity.ok("Prescription deleted successfully");
    }catch(Exception e){
        e.printStackTrace();
        return ResponseEntity.notFound().build();
    }
    }
}
