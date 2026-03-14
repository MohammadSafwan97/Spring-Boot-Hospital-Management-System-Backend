package com.safwantech.hms_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private Long totalPatients;
    private Long totalDoctors;
    private Long totalAppointments;
    private Long todayAppointments;
    private Long pendingAppointments;
    private Long completedAppointments;
    private Long newPatientsThisMonth;
    private Long emergencyPatients;



}
