package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.ClinicDto;
import com.safwantech.hms_backend.security.CurrentUserUtil;
import com.safwantech.hms_backend.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;
    private final CurrentUserUtil currentUserUtil;

    @GetMapping("/current")
    public ResponseEntity<ClinicDto> getCurrentClinic() {
        return ResponseEntity.ok(clinicService.getCurrentClinic(currentUserUtil.getCurrentClinicId()));
    }

    @PutMapping("/current")
    public ResponseEntity<ClinicDto> updateCurrentClinic(
            @RequestBody @Valid ClinicDto clinicDto
    ) {
        return ResponseEntity.ok(clinicService.updateCurrentClinic(currentUserUtil.getCurrentClinicId(), clinicDto));
    }
}
