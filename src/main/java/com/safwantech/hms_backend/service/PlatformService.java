package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.*;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.User;
import com.safwantech.hms_backend.entity.type.Role;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlatformService {

    private static final String PASSWORD_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789";
    private static final int GENERATED_PASSWORD_LENGTH = 12;

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<ClinicDto> getAllClinics() {
        return clinicRepository.findAll()
                .stream()
                .map(this::mapClinic)
                .toList();
    }

    @Transactional
    public ClinicDto createClinic(ClinicDto clinicDto) {
        validateClinicUniqueness(clinicDto, null);

        Clinic clinic = Clinic.builder()
                .name(clinicDto.getName())
                .subdomain(clinicDto.getSubdomain())
                .email(clinicDto.getEmail())
                .phone(clinicDto.getPhone())
                .address(clinicDto.getAddress())
                .logoUrl(clinicDto.getLogoUrl())
                .timezone(clinicDto.getTimezone())
                .active(true)
                .trial(true)
                .build();

        Clinic savedClinic = clinicRepository.save(clinic);
        log.info("AUDIT clinic.created clinicId={} subdomain={}", savedClinic.getId(), savedClinic.getSubdomain());
        return mapClinic(savedClinic);
    }

    @Transactional
    public ClinicDto updateClinic(Long clinicId, ClinicDto clinicDto) {
        Clinic clinic = findClinic(clinicId);
        validateClinicUniqueness(clinicDto, clinicId);

        clinic.setName(clinicDto.getName());
        clinic.setSubdomain(clinicDto.getSubdomain());
        clinic.setEmail(clinicDto.getEmail());
        clinic.setPhone(clinicDto.getPhone());
        clinic.setAddress(clinicDto.getAddress());
        clinic.setLogoUrl(clinicDto.getLogoUrl());
        clinic.setTimezone(clinicDto.getTimezone());

        Clinic savedClinic = clinicRepository.save(clinic);
        log.info("AUDIT clinic.updated clinicId={} subdomain={}", savedClinic.getId(), savedClinic.getSubdomain());
        return mapClinic(savedClinic);
    }

    @Transactional
    public ClinicDto updateClinicStatus(Long clinicId, PlatformClinicStatusUpdateDto statusUpdateDto) {
        Clinic clinic = findClinic(clinicId);
        clinic.setActive(statusUpdateDto.isActive());
        Clinic savedClinic = clinicRepository.save(clinic);
        log.info("AUDIT clinic.status-updated clinicId={} active={}", savedClinic.getId(), savedClinic.getActive());
        return mapClinic(savedClinic);
    }

    @Transactional
    public PlatformClinicAdminCredentialDto createFirstClinicAdmin(
            Long clinicId,
            PlatformClinicAdminCreateRequestDto requestDto
    ) {
        Clinic clinic = findClinic(clinicId);

        boolean hasAdmin = userRepository.findByClinicId(clinicId)
                .stream()
                .anyMatch(user -> user.getRole() == Role.ADMIN);

        if (hasAdmin) {
            throw new IllegalArgumentException("This clinic already has an admin user");
        }

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        String temporaryPassword = generateTemporaryPassword();

        User user = User.builder()
                .clinic(clinic)
                .username(requestDto.getUsername())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(temporaryPassword))
                .role(Role.ADMIN)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);
        log.info("AUDIT clinic.first-admin-created clinicId={} userId={}", clinic.getId(), savedUser.getId());

        return PlatformClinicAdminCredentialDto.builder()
                .clinicId(clinic.getId())
                .clinicName(clinic.getName())
                .userId(savedUser.getId())
                .username(savedUser.getUsername())
                .temporaryPassword(temporaryPassword)
                .build();
    }

    @Transactional
    public PlatformPasswordResetDto resetClinicAdminPassword(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (user.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("Password reset is only available for clinic admin users");
        }

        String temporaryPassword = generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(temporaryPassword));
        userRepository.save(user);
        log.info("AUDIT clinic.admin-password-reset clinicId={} userId={}", user.getClinic() != null ? user.getClinic().getId() : null, user.getId());

        return PlatformPasswordResetDto.builder()
                .temporaryPassword(temporaryPassword)
                .build();
    }

    private Clinic findClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private ClinicDto mapClinic(Clinic clinic) {
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

    private void validateClinicUniqueness(ClinicDto clinicDto, Long clinicId) {
        if (
                clinicDto.getSubdomain() != null &&
                !clinicDto.getSubdomain().isBlank() &&
                clinicRepository.existsBySubdomain(clinicDto.getSubdomain())
        ) {
            boolean matchesSameClinic = clinicId != null && clinicRepository.findById(clinicId)
                    .map(existingClinic -> clinicDto.getSubdomain().equals(existingClinic.getSubdomain()))
                    .orElse(false);

            if (!matchesSameClinic) {
                throw new IllegalArgumentException("Clinic code is already in use");
            }
        }
    }

    private String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < GENERATED_PASSWORD_LENGTH; i++) {
            int index = random.nextInt(PASSWORD_CHARS.length());
            password.append(PASSWORD_CHARS.charAt(index));
        }

        return password.toString();
    }
}
