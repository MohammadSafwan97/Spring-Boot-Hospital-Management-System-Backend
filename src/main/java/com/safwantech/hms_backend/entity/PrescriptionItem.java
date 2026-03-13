package com.safwantech.hms_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PrescriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicine;

    private String dosage;

    private String instruction;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    @JsonIgnore
    private Prescription prescription;
}