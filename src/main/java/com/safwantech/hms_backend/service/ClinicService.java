package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.ClinicDto;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClinicService {

    private final ClinicRepository clinicRepository;

    @Transactional(readOnly = true)
    public ClinicDto getCurrentClinic(Long clinicId) {
        Clinic clinic = findClinic(clinicId);
        return mapToDto(clinic);
    }

    @Transactional
    public ClinicDto updateCurrentClinic(Long clinicId, ClinicDto clinicDto) {
        Clinic clinic = findClinic(clinicId);

        clinic.setName(clinicDto.getName());
        clinic.setSubdomain(clinicDto.getSubdomain());
        clinic.setEmail(clinicDto.getEmail());
        clinic.setPhone(clinicDto.getPhone());
        clinic.setAddress(clinicDto.getAddress());
        clinic.setLogoUrl(clinicDto.getLogoUrl());
        clinic.setTimezone(clinicDto.getTimezone());

        Clinic savedClinic = clinicRepository.save(clinic);
        log.info("AUDIT clinic.settings-updated clinicId={} subdomain={}", savedClinic.getId(), savedClinic.getSubdomain());
        return mapToDto(savedClinic);
    }

    private Clinic findClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private ClinicDto mapToDto(Clinic clinic) {
        return ClinicDto.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .subdomain(clinic.getSubdomain())
                .email(clinic.getEmail())
                .phone(clinic.getPhone())
                .address(clinic.getAddress())
                .logoUrl(clinic.getLogoUrl())
                .timezone(clinic.getTimezone())
                .active(clinic.getActive())
                .build();
    }
}
