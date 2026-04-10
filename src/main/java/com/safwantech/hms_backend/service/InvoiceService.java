package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.InvoiceDto;
import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.Invoice;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicRepository clinicRepository;

    @Transactional
    public InvoiceDto createInvoice(InvoiceDto dto) {
        Clinic clinic = getClinic(dto.getClinicId());
        Appointment appointment = getAppointment(clinic.getId(), dto.getAppointmentId());

        if (invoiceRepository.existsByAppointmentIdAndClinicId(dto.getAppointmentId(), clinic.getId())) {
            throw new IllegalArgumentException("An invoice already exists for this appointment");
        }

        Invoice invoice = Invoice.builder()
                .invoiceDate(dto.getInvoiceDate() != null ? dto.getInvoiceDate() : LocalDateTime.now())
                .consultationFee(safeAmount(dto.getConsultationFee()))
                .extraCharges(safeAmount(dto.getExtraCharges()))
                .paymentStatus(dto.getPaymentStatus())
                .paymentMethod(dto.getPaymentMethod())
                .tokenNumber(trimToNull(dto.getTokenNumber()))
                .notes(trimToNull(dto.getNotes()))
                .clinic(clinic)
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .appointment(appointment)
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);
        savedInvoice.setInvoiceNumber(buildInvoiceNumber(savedInvoice.getId()));

        return mapToDto(invoiceRepository.save(savedInvoice));
    }

    @Transactional
    public List<InvoiceDto> getAllInvoices(Long clinicId) {
        return invoiceRepository.findByClinicIdOrderByInvoiceDateDesc(clinicId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public InvoiceDto getInvoiceById(Long clinicId, Long id) {
        return mapToDto(findInvoice(clinicId, id));
    }

    @Transactional
    public InvoiceDto updateInvoice(Long clinicId, Long id, InvoiceDto dto) {
        Invoice invoice = findInvoice(clinicId, id);

        invoice.setConsultationFee(safeAmount(dto.getConsultationFee()));
        invoice.setExtraCharges(safeAmount(dto.getExtraCharges()));
        invoice.setPaymentStatus(dto.getPaymentStatus());
        invoice.setPaymentMethod(dto.getPaymentMethod());
        invoice.setTokenNumber(trimToNull(dto.getTokenNumber()));
        invoice.setNotes(trimToNull(dto.getNotes()));

        return mapToDto(invoiceRepository.save(invoice));
    }

    @Transactional
    public void deleteInvoice(Long clinicId, Long id) {
        Invoice invoice = findInvoice(clinicId, id);
        invoiceRepository.delete(invoice);
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private Appointment getAppointment(Long clinicId, Long appointmentId) {
        return appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + appointmentId + " for clinic: " + clinicId
                ));
    }

    private Invoice findInvoice(Long clinicId, Long id) {
        return invoiceRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invoice not found with id: " + id + " for clinic: " + clinicId
                ));
    }

    private InvoiceDto mapToDto(Invoice invoice) {
        InvoiceDto dto = new InvoiceDto();
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setClinicId(invoice.getClinic().getId());
        dto.setAppointmentId(invoice.getAppointment().getId());
        dto.setPatientId(invoice.getPatient().getId());
        dto.setDoctorId(invoice.getDoctor().getId());
        dto.setConsultationFee(invoice.getConsultationFee());
        dto.setExtraCharges(invoice.getExtraCharges());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setPaymentStatus(invoice.getPaymentStatus());
        dto.setPaymentMethod(invoice.getPaymentMethod());
        dto.setTokenNumber(invoice.getTokenNumber());
        dto.setNotes(invoice.getNotes());
        return dto;
    }

    private String buildInvoiceNumber(Long id) {
        return String.format("INV-%06d", id);
    }

    private BigDecimal safeAmount(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
