package com.safwantech.hms_backend.entity;

import jakarta.persistence.*;
import lombok.*;

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