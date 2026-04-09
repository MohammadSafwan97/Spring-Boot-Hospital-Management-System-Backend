package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem,Long> {
    List<PrescriptionItem> findByPrescriptionIdAndPrescriptionClinicId(Long prescriptionId, Long clinicId);
    Optional<PrescriptionItem> findByIdAndPrescriptionClinicId(Long id, Long clinicId);
}
