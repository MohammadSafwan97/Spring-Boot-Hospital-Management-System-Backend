package com.safwantech.hms_backend.entity;

import com.safwantech.hms_backend.entity.type.BloodGroupType;
import com.safwantech.hms_backend.entity.type.PatientType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String patientId;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BloodGroupType bloodGroupType;

    @Past
    private LocalDate dateOfBirth;

    @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Invalid gender")
    private String gender;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PatientType patientType;
    @Email
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phoneNumber;

    @Size(max = 200)
    private String address;

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
