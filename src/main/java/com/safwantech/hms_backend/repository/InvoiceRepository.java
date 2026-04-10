package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClinicIdOrderByInvoiceDateDesc(Long clinicId);
    Optional<Invoice> findByIdAndClinicId(Long id, Long clinicId);
    boolean existsByAppointmentIdAndClinicId(Long appointmentId, Long clinicId);
}
