package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.ClinicServiceDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClinicServiceDefinitionRepository extends JpaRepository<ClinicServiceDefinition, Long> {
    List<ClinicServiceDefinition> findByClinicIdOrderByNameAsc(Long clinicId);
    List<ClinicServiceDefinition> findByClinicIdAndActiveTrueOrderByNameAsc(Long clinicId);
    Optional<ClinicServiceDefinition> findByIdAndClinicId(Long id, Long clinicId);
}
