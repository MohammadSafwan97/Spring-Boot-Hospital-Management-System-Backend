package com.safwantech.hms_backend.controller;

import com.safwantech.hms_backend.dto.DashboardStatsDto;
import com.safwantech.hms_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
@RestController
public class DashboardStatsController {
    private final DashboardService dashboardService;
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getStats(@RequestParam Long clinicId){
        DashboardStatsDto stats = dashboardService.getDashboardStats(clinicId);
        return ResponseEntity.ok(stats);
    }
}
