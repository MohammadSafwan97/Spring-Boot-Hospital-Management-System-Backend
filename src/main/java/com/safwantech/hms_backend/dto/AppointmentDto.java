package com.safwantech.hms_backend.dto;


import com.safwantech.hms_backend.entity.type.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

    
@Setter
@Getter
public class AppointmentDto {
    private Long id;
    private Long clinicId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String timeSlot;
    private String remarks;
    private AppointmentStatus status;
    private Long patientId;
    private Long doctorId;





}

