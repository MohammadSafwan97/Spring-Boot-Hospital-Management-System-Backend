package com.safwantech.hms_backend.dto;

import com.safwantech.hms_backend.entity.type.PaymentMethod;
import com.safwantech.hms_backend.entity.type.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class InvoiceDto {

    private Long id;
    private String invoiceNumber;
    private LocalDateTime invoiceDate;
    private Long clinicId;

    @NotNull
    private Long appointmentId;

    private Long patientId;
    private Long doctorId;
    private Long serviceId;
    private String serviceName;
    private BigDecimal servicePrice;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal consultationFee;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal extraCharges;

    private BigDecimal totalAmount;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private PaymentMethod paymentMethod;

    @Size(max = 50)
    private String tokenNumber;

    @Size(max = 255)
    private String notes;
}
