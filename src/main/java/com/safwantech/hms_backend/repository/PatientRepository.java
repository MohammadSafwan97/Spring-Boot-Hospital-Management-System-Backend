package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.entity.type.PatientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Long countByCreatedAtAfter(LocalDateTime localDateTime);

    Long countByPatientType(PatientType patientType);
}
