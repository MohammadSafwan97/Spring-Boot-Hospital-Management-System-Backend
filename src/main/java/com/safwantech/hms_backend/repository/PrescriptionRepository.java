package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    List<Prescription> findByClinicId(Long clinicId);
    Optional<Prescription> findByIdAndClinicId(Long id, Long clinicId);
}
