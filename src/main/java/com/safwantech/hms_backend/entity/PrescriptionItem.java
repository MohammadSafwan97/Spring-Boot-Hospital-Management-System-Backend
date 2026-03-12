package com.safwantech.hms_backend.entity;

import jakarta.persistence.*;

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
    private Prescription prescription;

}
