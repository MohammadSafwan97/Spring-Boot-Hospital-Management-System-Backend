package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.DashboardStatsDto;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DashboardStatsDto getDashboardStats(){
    Long totalPatients=patientRepository.count();
    Long totalDoctors=doctorRepository.count();
    long totalAppointments=appointmentRepository.count();
    return new DashboardStatsDto(totalPatients,totalDoctors,totalAppointments);
}}
