package com.safwantech.hms_backend.entity;

import com.safwantech.hms_backend.entity.type.BloodGroupType;
import com.safwantech.hms_backend.entity.type.PatientType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "patients",
        indexes = {
                @Index(name = "idx_patient_clinic", columnList = "clinic_id"),
                @Index(name = "idx_patient_email", columnList = "email"),
                @Index(name = "idx_patient_phone", columnList = "phoneNumber")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroupType bloodGroupType;

    @Past
    private LocalDate dateOfBirth;

    @Pattern(regexp = "MALE|FEMALE|OTHER")
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatientType patientType;

    private String email;

    private String phoneNumber;

    @Size(max = 200)
    private String address;

    @Column(nullable = false)
    private Boolean deleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}