package com.safwantech.hms_backend.entity;

import com.safwantech.hms_backend.entity.type.PaymentMethod;
import com.safwantech.hms_backend.entity.type.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "invoices",
        indexes = {
                @Index(name = "idx_invoice_clinic", columnList = "clinic_id"),
                @Index(name = "idx_invoice_patient", columnList = "patient_id"),
                @Index(name = "idx_invoice_doctor", columnList = "doctor_id"),
                @Index(name = "idx_invoice_appointment", columnList = "appointment_id"),
                @Index(name = "idx_invoice_status", columnList = "paymentStatus")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String invoiceNumber;

    @Column(nullable = false)
    private LocalDateTime invoiceDate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal consultationFee;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal extraCharges;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(length = 50)
    private String tokenNumber;

    @Column(length = 255)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        if (invoiceDate == null) {
            invoiceDate = LocalDateTime.now();
        }

        if (consultationFee == null) {
            consultationFee = BigDecimal.ZERO;
        }

        if (extraCharges == null) {
            extraCharges = BigDecimal.ZERO;
        }

        totalAmount = consultationFee.add(extraCharges);
    }

    @PreUpdate
    public void onUpdate() {
        if (consultationFee == null) {
            consultationFee = BigDecimal.ZERO;
        }

        if (extraCharges == null) {
            extraCharges = BigDecimal.ZERO;
        }

        totalAmount = consultationFee.add(extraCharges);
    }
}
