package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.ClinicServiceDto;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.ClinicServiceDefinition;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.ClinicServiceDefinitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClinicServiceCatalogService {

    private final ClinicServiceDefinitionRepository clinicServiceDefinitionRepository;
    private final ClinicRepository clinicRepository;

    @Transactional(readOnly = true)
    public List<ClinicServiceDto> getAllServices(Long clinicId, boolean activeOnly) {
        List<ClinicServiceDefinition> services = activeOnly
                ? clinicServiceDefinitionRepository.findByClinicIdAndActiveTrueOrderByNameAsc(clinicId)
                : clinicServiceDefinitionRepository.findByClinicIdOrderByNameAsc(clinicId);

        return services.stream().map(this::mapToDto).toList();
    }

    @Transactional
    public ClinicServiceDto createService(Long clinicId, ClinicServiceDto dto) {
        Clinic clinic = getClinic(clinicId);
        ClinicServiceDefinition service = ClinicServiceDefinition.builder()
                .clinic(clinic)
                .name(dto.getName().trim())
                .price(safePrice(dto.getPrice()))
                .description(trimToNull(dto.getDescription()))
                .active(dto.getActive() == null || dto.getActive())
                .build();

        ClinicServiceDefinition saved = clinicServiceDefinitionRepository.save(service);
        log.info("AUDIT clinic-service.created clinicId={} serviceId={} name={}", clinicId, saved.getId(), saved.getName());
        return mapToDto(saved);
    }

    @Transactional
    public ClinicServiceDto updateService(Long clinicId, Long serviceId, ClinicServiceDto dto) {
        ClinicServiceDefinition service = findService(clinicId, serviceId);
        service.setName(dto.getName().trim());
        service.setPrice(safePrice(dto.getPrice()));
        service.setDescription(trimToNull(dto.getDescription()));
        service.setActive(dto.getActive() == null || dto.getActive());

        ClinicServiceDefinition saved = clinicServiceDefinitionRepository.save(service);
        log.info("AUDIT clinic-service.updated clinicId={} serviceId={} active={}", clinicId, saved.getId(), saved.getActive());
        return mapToDto(saved);
    }

    @Transactional
    public ClinicServiceDto updateServiceStatus(Long clinicId, Long serviceId, boolean active) {
        ClinicServiceDefinition service = findService(clinicId, serviceId);
        service.setActive(active);
        ClinicServiceDefinition saved = clinicServiceDefinitionRepository.save(service);
        log.info("AUDIT clinic-service.status-updated clinicId={} serviceId={} active={}", clinicId, saved.getId(), saved.getActive());
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public ClinicServiceDefinition getActiveServiceEntity(Long clinicId, Long serviceId) {
        ClinicServiceDefinition service = findService(clinicId, serviceId);
        if (!Boolean.TRUE.equals(service.getActive())) {
            throw new IllegalArgumentException("Selected service is inactive");
        }
        return service;
    }

    @Transactional(readOnly = true)
    public ClinicServiceDefinition findService(Long clinicId, Long serviceId) {
        return clinicServiceDefinitionRepository.findByIdAndClinicId(serviceId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId + " for clinic: " + clinicId));
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private ClinicServiceDto mapToDto(ClinicServiceDefinition service) {
        ClinicServiceDto dto = new ClinicServiceDto();
        dto.setId(service.getId());
        dto.setClinicId(service.getClinic().getId());
        dto.setName(service.getName());
        dto.setPrice(service.getPrice());
        dto.setDescription(service.getDescription());
        dto.setActive(service.getActive());
        return dto;
    }

    private BigDecimal safePrice(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
