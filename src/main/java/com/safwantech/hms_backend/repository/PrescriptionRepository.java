package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
}
