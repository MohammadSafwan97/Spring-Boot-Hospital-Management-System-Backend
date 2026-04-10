package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.*;
import com.safwantech.hms_backend.service.PlatformService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/platform")
public class PlatformController {

    private final PlatformService platformService;

    @GetMapping("/clinics")
    public ResponseEntity<List<ClinicDto>> getAllClinics() {
        return ResponseEntity.ok(platformService.getAllClinics());
    }

    @PostMapping("/clinics")
    public ResponseEntity<ClinicDto> createClinic(@RequestBody @Valid ClinicDto clinicDto) {
        return ResponseEntity.ok(platformService.createClinic(clinicDto));
    }

    @PutMapping("/clinics/{clinicId}")
    public ResponseEntity<ClinicDto> updateClinic(
            @PathVariable Long clinicId,
            @RequestBody @Valid ClinicDto clinicDto
    ) {
        return ResponseEntity.ok(platformService.updateClinic(clinicId, clinicDto));
    }

    @PatchMapping("/clinics/{clinicId}/status")
    public ResponseEntity<ClinicDto> updateClinicStatus(
            @PathVariable Long clinicId,
            @RequestBody PlatformClinicStatusUpdateDto statusUpdateDto
    ) {
        return ResponseEntity.ok(platformService.updateClinicStatus(clinicId, statusUpdateDto));
    }

    @PostMapping("/clinics/{clinicId}/admin")
    public ResponseEntity<PlatformClinicAdminCredentialDto> createFirstClinicAdmin(
            @PathVariable Long clinicId,
            @RequestBody @Valid PlatformClinicAdminCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(platformService.createFirstClinicAdmin(clinicId, requestDto));
    }

    @PatchMapping("/users/{userId}/password")
    public ResponseEntity<PlatformPasswordResetDto> resetClinicAdminPassword(@PathVariable Long userId) {
        return ResponseEntity.ok(platformService.resetClinicAdminPassword(userId));
    }
}
