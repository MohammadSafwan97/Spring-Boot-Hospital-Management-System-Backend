package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.DoctorDto;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto){
        try {
            DoctorDto createdDoctor = doctorService.createDoctor((doctorDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorDto);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    ResponseEntity<List<DoctorDto>> getAllDoctors(){
       List<DoctorDto> doctors=doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
}
