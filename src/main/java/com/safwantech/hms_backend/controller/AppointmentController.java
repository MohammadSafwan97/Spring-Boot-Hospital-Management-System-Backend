package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.AppointmentDto;
import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody
                                                                AppointmentDto appointmentDto){
        try{
          return ResponseEntity.status(HttpStatus.CREATED)
                  .body(appointmentService.createAppointment(appointmentDto));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();

        }
    }
}
