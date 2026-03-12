package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem,Long> {
    Collection<Object> findByPrescriptionId(Long prescriptionId);
}
