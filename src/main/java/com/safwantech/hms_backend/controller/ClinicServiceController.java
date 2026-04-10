package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.ClinicServiceDto;
import com.safwantech.hms_backend.security.CurrentUserUtil;
import com.safwantech.hms_backend.service.ClinicServiceCatalogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clinic-services")
public class ClinicServiceController {

    private final ClinicServiceCatalogService clinicServiceCatalogService;
    private final CurrentUserUtil currentUserUtil;

    @GetMapping
    public ResponseEntity<List<ClinicServiceDto>> getClinicServices(
            @RequestParam(name = "activeOnly", defaultValue = "false") boolean activeOnly
    ) {
        return ResponseEntity.ok(clinicServiceCatalogService.getAllServices(currentUserUtil.getCurrentClinicId(), activeOnly));
    }

    @PostMapping
    public ResponseEntity<ClinicServiceDto> createClinicService(@Valid @RequestBody ClinicServiceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clinicServiceCatalogService.createService(currentUserUtil.getCurrentClinicId(), dto));
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ClinicServiceDto> updateClinicService(
            @PathVariable Long serviceId,
            @Valid @RequestBody ClinicServiceDto dto
    ) {
        return ResponseEntity.ok(clinicServiceCatalogService.updateService(currentUserUtil.getCurrentClinicId(), serviceId, dto));
    }

    @PatchMapping("/{serviceId}/status")
    public ResponseEntity<ClinicServiceDto> updateClinicServiceStatus(
            @PathVariable Long serviceId,
            @RequestBody ClinicServiceDto dto
    ) {
        boolean active = dto.getActive() == null || dto.getActive();
        return ResponseEntity.ok(clinicServiceCatalogService.updateServiceStatus(currentUserUtil.getCurrentClinicId(), serviceId, active));
    }
}
