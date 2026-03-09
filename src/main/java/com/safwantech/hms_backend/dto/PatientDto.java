package com.safwantech.hms_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientDto {
    private Long id;

    private String patientId;

    private String name;

    private Integer age;

    private String gender;

    private String phone;

    private String bloodGroup;

    private String patientType; // Inpatient / Outpatient / Emergency

    private String ward;

    private String doctorAssigned;
}
