package com.safwantech.hms_backend.controller;


import com.safwantech.hms_backend.dto.PatientDto;
import com.safwantech.hms_backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:5173")

public class PatientController {

    private final PatientService patientService;



    /* ---------------- CREATE ---------------- */

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(
            @RequestBody PatientDto patientDto) {
try{
        PatientDto createdPatient = patientService.createPatient(patientDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }
catch (Exception e){
    return ResponseEntity.badRequest().build();
}
    }

    /* ---------------- GET ALL ---------------- */

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {

        return ResponseEntity.ok(patientService.getAllPatients());
    }

    /* ---------------- GET BY ID ---------------- */

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(
            @PathVariable Long id) {

        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    /* ---------------- UPDATE ---------------- */

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientDto patientDto) {

        return ResponseEntity.ok(
                patientService.updatePatient(id, patientDto)
        );
    }

    /* ---------------- DELETE ---------------- */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok("Patient deleted successfully");
    }

}