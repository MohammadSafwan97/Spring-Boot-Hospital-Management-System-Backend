package com.safwantech.hms_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PatientProfileDto {
    private PatientDto patient;
    private LocalDate lastVisitDate;
    private Long totalVisits;
    private Long totalInvoices;
    private BigDecimal totalBilledAmount;
    private List<AppointmentDto> appointments;
    private List<InvoiceDto> invoices;
}
