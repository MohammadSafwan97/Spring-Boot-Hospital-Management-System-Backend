package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.DoctorDto;
import com.safwantech.hms_backend.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto){
        try {
            DoctorDto createdDoctor = doctorService.createDoctor((doctorDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    ResponseEntity<List<DoctorDto>> getAllDoctors(@RequestParam Long clinicId){
       List<DoctorDto> doctors = doctorService.getAllDoctors(clinicId);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{doctorId}")
    ResponseEntity<DoctorDto> getById(@RequestParam Long clinicId, @PathVariable Long doctorId){
        DoctorDto doctorDto = doctorService.getById(clinicId, doctorId);
        return ResponseEntity.ok(doctorDto);
    }

    @PutMapping("/{doctorId}")

    ResponseEntity<DoctorDto> updatePatient(@RequestParam Long clinicId,
                                            @PathVariable Long doctorId,
                                            @RequestBody DoctorDto doctorDto){
        try{
        DoctorDto updatedDoctor = doctorService.updateDoctor(clinicId, doctorId, doctorDto);
        return ResponseEntity.ok(updatedDoctor);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@RequestParam Long clinicId, @PathVariable Long doctorId){
        doctorService.deleteDoctor(clinicId, doctorId);
        return ResponseEntity.ok("Doctor deleted successfully");
    }


}
