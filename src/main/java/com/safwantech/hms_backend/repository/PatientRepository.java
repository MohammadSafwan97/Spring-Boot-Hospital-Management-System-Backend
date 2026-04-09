package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.entity.type.PatientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findByClinicId(Long clinicId);
    Optional<Patient> findByIdAndClinicId(Long id, Long clinicId);
    Long countByClinicId(Long clinicId);
    Long countByCreatedAtAfter(LocalDateTime localDateTime);
    Long countByClinicIdAndCreatedAtAfter(Long clinicId, LocalDateTime localDateTime);

    Long countByPatientType(PatientType patientType);
    Long countByClinicIdAndPatientType(Long clinicId, PatientType patientType);
}
