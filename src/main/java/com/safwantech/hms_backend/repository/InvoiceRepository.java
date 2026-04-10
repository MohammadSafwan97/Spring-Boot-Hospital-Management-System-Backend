package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Invoice;
import com.safwantech.hms_backend.entity.type.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClinicIdOrderByInvoiceDateDesc(Long clinicId);
    List<Invoice> findByPatientIdAndClinicIdOrderByInvoiceDateDesc(Long patientId, Long clinicId);
    Optional<Invoice> findByIdAndClinicId(Long id, Long clinicId);
    boolean existsByAppointmentIdAndClinicId(Long appointmentId, Long clinicId);
    Long countByClinicIdAndPaymentStatus(Long clinicId, PaymentStatus paymentStatus);

    @Query("select coalesce(sum(i.totalAmount), 0) from Invoice i where i.clinic.id = :clinicId and i.invoiceDate >= :start and i.invoiceDate < :end")
    BigDecimal sumTotalAmountForClinicBetween(Long clinicId, LocalDateTime start, LocalDateTime end);

    @Query("select coalesce(sum(i.totalAmount), 0) from Invoice i where i.patient.id = :patientId and i.clinic.id = :clinicId")
    BigDecimal sumTotalAmountByPatientAndClinic(Long patientId, Long clinicId);
}
