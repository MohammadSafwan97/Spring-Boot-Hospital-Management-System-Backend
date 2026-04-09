package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    boolean existsBySubdomain(String subdomain);
}
