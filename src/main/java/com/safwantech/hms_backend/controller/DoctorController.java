package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.DoctorDto;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto){
        try {
            DoctorDto createdDoctor = doctorService.createDoctor((doctorDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorDto);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    ResponseEntity<List<DoctorDto>> getAllDoctors(){
       List<DoctorDto> doctors=doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{doctorId}")
    ResponseEntity<DoctorDto> getById(@PathVariable Long doctorId){
        DoctorDto doctorDto=doctorService.getById(doctorId);
        return ResponseEntity.ok(doctorDto);
    }

    @PutMapping("/{doctorId}")

    ResponseEntity<DoctorDto> updatePatient(@PathVariable Long doctorId,
                                            @RequestBody DoctorDto doctorDto){
        try{
        DoctorDto updatedDoctor=doctorService.updateDoctor(doctorId,doctorDto);
        return ResponseEntity.ok(updatedDoctor);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        }
    @DeleteMapping("/{doctorId}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long doctorId){
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Patient deleted successfully");
    }


}