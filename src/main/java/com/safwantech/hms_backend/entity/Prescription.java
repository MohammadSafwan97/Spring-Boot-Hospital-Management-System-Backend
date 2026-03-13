package com.safwantech.hms_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String diagnosis;

    private String medication;

    private String dosage;

    private String instruction;

    @OneToOne
    @JoinColumn(name="appointment_id")
    @JsonIgnore
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name="patient_id")
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescriptionItem> prescriptionItems = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}