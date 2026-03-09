package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
