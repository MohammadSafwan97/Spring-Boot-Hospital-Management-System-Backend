package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository <Doctor,Long> {

    List<Doctor> findByClinicId(Long clinicId);

    Optional<Doctor> findByIdAndClinicId(Long id, Long clinicId);

    Long countByClinicId(Long clinicId);
}
