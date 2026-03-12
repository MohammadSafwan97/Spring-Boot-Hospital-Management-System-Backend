package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.PrescriptionDto;
import com.safwantech.hms_backend.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(@RequestBody PrescriptionDto prescriptionDto){
        try{
            PrescriptionDto savedPrescription=prescriptionService.createPrescription(prescriptionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPrescription);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
