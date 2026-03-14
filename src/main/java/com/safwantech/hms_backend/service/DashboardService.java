package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.DashboardStatsDto;
import com.safwantech.hms_backend.entity.type.AppointmentStatus;
import com.safwantech.hms_backend.entity.type.PatientType;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    Long emergencyPatients=patientRepository.countByPatientType(PatientType.EMERGENCY);
    Long todayAppointments=appointmentRepository.countByAppointmentDate(LocalDate.now());
    Long pendingAppointments=appointmentRepository.countByStatus(AppointmentStatus.SCHEDULED);
    Long completedAppointments=appointmentRepository.countByStatus(AppointmentStatus.COMPLETED);
    Long newPatientsThisMonth=patientRepository.countByCreatedAtAfter(
            LocalDate.now().withDayOfMonth(1).atStartOfDay()
    );
        return new DashboardStatsDto(
                totalPatients,
                totalDoctors,
                totalAppointments,
                todayAppointments,
                pendingAppointments,
                completedAppointments,
                newPatientsThisMonth,
                emergencyPatients
        );

}}
