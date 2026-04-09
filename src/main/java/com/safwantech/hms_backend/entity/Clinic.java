package com.safwantech.hms_backend.entity;

import com.safwantech.hms_backend.entity.type.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "clinics",
        indexes = {
                @Index(name = "idx_clinic_subdomain", columnList = "subdomain"),
                @Index(name = "idx_clinic_active", columnList = "active")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Business identity
    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String subdomain;

    private String email;

    private String phone;

    private String address;

    private String logoUrl;

    private String timezone;

    // SaaS lifecycle
    @Column(nullable = false)
    private Boolean active = true;


    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    private LocalDate subscriptionStartDate;

    private LocalDate subscriptionEndDate;

    // Optional (for grace period handling)
    private Boolean trial = false;

    // Audit
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
