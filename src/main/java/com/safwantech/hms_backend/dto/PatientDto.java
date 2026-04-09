package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.BloodGroupType;
import com.safwantech.hms_backend.entity.type.PatientType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDto {

    private Long id;

    @NotNull
    private Long clinicId;

    @NotBlank
    private String name;

    @NotNull
    private BloodGroupType bloodGroupType;

    private String gender;

    private String phoneNumber;

    @Email
    private String email;

    private LocalDate dateOfBirth;

    private String address;

    @NotNull
    private PatientType patientType;
}
