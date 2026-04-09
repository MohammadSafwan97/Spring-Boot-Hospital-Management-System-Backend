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

    public DashboardStatsDto getDashboardStats(Long clinicId){
    Long totalPatients = patientRepository.countByClinicId(clinicId);
    Long totalDoctors = doctorRepository.countByClinicId(clinicId);
    long totalAppointments = appointmentRepository.findByClinicId(clinicId).size();
    Long emergencyPatients = patientRepository.countByClinicIdAndPatientType(clinicId, PatientType.EMERGENCY);
    Long todayAppointments = appointmentRepository.countByClinicIdAndAppointmentDate(clinicId, LocalDate.now());
    Long pendingAppointments = appointmentRepository.countByClinicIdAndStatus(clinicId, AppointmentStatus.SCHEDULED);
    Long completedAppointments = appointmentRepository.countByClinicIdAndStatus(clinicId, AppointmentStatus.COMPLETED);
    Long newPatientsThisMonth = patientRepository.countByClinicIdAndCreatedAtAfter(
            clinicId,
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
