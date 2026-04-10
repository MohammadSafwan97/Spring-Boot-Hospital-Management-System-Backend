package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.AppointmentDto;
import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.entity.ClinicServiceDefinition;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.entity.type.AppointmentStatus;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final ModelMapper modelMapper;
    private final ClinicServiceCatalogService clinicServiceCatalogService;

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        Clinic clinic = getClinic(appointmentDto.getClinicId());
        Doctor doctor = doctorRepository.findByIdAndClinicId(appointmentDto.getDoctorId(), clinic.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + appointmentDto.getDoctorId() + " for clinic: " + clinic.getId()));
        Patient patient = patientRepository.findByIdAndClinicId(appointmentDto.getPatientId(), clinic.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + appointmentDto.getPatientId() + " for clinic: " + clinic.getId()));
        ClinicServiceDefinition service = clinicServiceCatalogService.getActiveServiceEntity(clinic.getId(), appointmentDto.getServiceId());

        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        appointment.setClinic(clinic);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setService(service);
        appointment.setBilled(Boolean.TRUE.equals(appointmentDto.getBilled()));

        Appointment saved = appointmentRepository.save(appointment);
        log.info("AUDIT appointment.created clinicId={} appointmentId={} patientId={} serviceId={}", clinic.getId(), saved.getId(), patient.getId(), service.getId());
        return mapToDto(saved);
    }

    @Transactional
    public List<AppointmentDto> getAllAppointments(Long clinicId) {
        return appointmentRepository.findByClinicId(clinicId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public AppointmentDto getById(Long clinicId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId + " for clinic: " + clinicId));
        return mapToDto(appointment);
    }

    @Transactional
    public AppointmentDto updateAppointment(Long clinicId, Long appointmentId, AppointmentDto dto) {
        Clinic clinic = getClinic(clinicId);
        Patient patient = patientRepository.findByIdAndClinicId(dto.getPatientId(), clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId() + " for clinic: " + clinicId));
        Doctor doctor = doctorRepository.findByIdAndClinicId(dto.getDoctorId(), clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId() + " for clinic: " + clinicId));
        Appointment appointment = appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId + " for clinic: " + clinicId));
        ClinicServiceDefinition service = clinicServiceCatalogService.getActiveServiceEntity(clinicId, dto.getServiceId());

        appointment.setClinic(clinic);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setService(service);
        appointment.setStatus(dto.getStatus());
        appointment.setRemarks(dto.getRemarks());
        appointment.setSource(dto.getSource());
        appointment.setBilled(Boolean.TRUE.equals(dto.getBilled()));
        if (dto.getStatus() == AppointmentStatus.CHECKED_IN && appointment.getCheckedInAt() == null) {
            appointment.setCheckedInAt(LocalDateTime.now());
        } else if (dto.getStatus() != AppointmentStatus.CHECKED_IN) {
            appointment.setCheckedInAt(dto.getCheckedInAt());
        }

        Appointment saved = appointmentRepository.save(appointment);
        log.info("AUDIT appointment.updated clinicId={} appointmentId={} status={} serviceId={}", clinicId, saved.getId(), saved.getStatus(), service.getId());
        return mapToDto(saved);
    }

    @Transactional
    public AppointmentDto checkInAppointment(Long clinicId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId + " for clinic: " + clinicId));
        appointment.setStatus(AppointmentStatus.CHECKED_IN);
        if (appointment.getCheckedInAt() == null) {
            appointment.setCheckedInAt(LocalDateTime.now());
        }
        Appointment saved = appointmentRepository.save(appointment);
        log.info("AUDIT appointment.checked-in clinicId={} appointmentId={}", clinicId, saved.getId());
        return mapToDto(saved);
    }

    @Transactional
    public void deleteAppointment(Long clinicId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findByIdAndClinicId(appointmentId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + appointmentId + " for clinic: " + clinicId));
        appointmentRepository.delete(appointment);
        log.info("AUDIT appointment.deleted clinicId={} appointmentId={}", clinicId, appointmentId);
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    AppointmentDto mapToDto(Appointment appointment) {
        AppointmentDto appointmentDto = modelMapper.map(appointment, AppointmentDto.class);
        appointmentDto.setClinicId(appointment.getClinic().getId());
        appointmentDto.setDoctorId(appointment.getDoctor().getId());
        appointmentDto.setPatientId(appointment.getPatient().getId());
        appointmentDto.setServiceId(appointment.getService() != null ? appointment.getService().getId() : null);
        appointmentDto.setServiceName(appointment.getService() != null ? appointment.getService().getName() : null);
        appointmentDto.setServicePrice(appointment.getService() != null ? appointment.getService().getPrice() : null);
        appointmentDto.setBilled(Boolean.TRUE.equals(appointment.getBilled()));
        return appointmentDto;
    }
}




