package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.AppointmentDto;
import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/appointments")

public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody
                                                            AppointmentDto appointmentDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(appointmentService.createAppointment(appointmentDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();

        }
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("{appointmentId}")
    public ResponseEntity<AppointmentDto> getById(@PathVariable Long appointmentId){
        try {
            AppointmentDto appointmentDto = appointmentService.getById(appointmentId);
            return ResponseEntity.status(HttpStatus.OK).body(appointmentDto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateById(@PathVariable Long id, @RequestBody AppointmentDto appointmentDto) {
        try {
            AppointmentDto updatedAppointment = appointmentService.updateAppoitnment(id, appointmentDto);
            return ResponseEntity.ok(updatedAppointment);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id){

        appointmentService.deleteAppointment(id);

        return ResponseEntity.ok("Appointment deleted successfully");

    }

}
