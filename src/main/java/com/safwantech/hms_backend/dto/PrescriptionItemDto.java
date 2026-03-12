package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.Prescription;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionItemDto {
    private String medicine;

    private String dosage;

    private String instruction;

    private Long prescriptionId;
}
