package com.safwantech.hms_backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionDto {


    private String diagnosis;

    private String medication;

    private String dosage;

    private String instruction;

    private Long appointment_id;

    private Long patient_id;

    private Long doctor_id;
}
