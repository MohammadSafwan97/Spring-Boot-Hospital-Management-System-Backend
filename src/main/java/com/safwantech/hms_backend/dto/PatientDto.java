package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.BloodGroupType;
import com.safwantech.hms_backend.entity.type.PatientType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PatientDto {
    private Long id;

    private String patientId;

    private String name;

    private BloodGroupType bloodGroupType;

    private String gender;

    private String phoneNumber;

    private String email;

    private LocalDate dateOfBirth;

    private String address;

    private String bloodGroup;

    private PatientType patientType; // Inpatient / Outpatient / Emergency

    private String ward;

    private String doctorAssigned;
}
