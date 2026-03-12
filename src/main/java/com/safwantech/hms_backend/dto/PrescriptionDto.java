package com.safwantech.hms_backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PrescriptionDto {

    private Long id;

    private String diagnosis;
    private String medication;
    private String dosage;
    private String instruction;

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;

    private List<PrescriptionItemDto> items;
}
