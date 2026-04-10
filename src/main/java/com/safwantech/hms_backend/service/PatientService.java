package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.AppointmentDto;
import com.safwantech.hms_backend.dto.InvoiceDto;
import com.safwantech.hms_backend.dto.PatientDto;
import com.safwantech.hms_backend.dto.PatientProfileDto;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.InvoiceRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final AppointmentRepository appointmentRepository;
    private final InvoiceRepository invoiceRepository;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;
    private final InvoiceService invoiceService;

    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        Clinic clinic = getClinic(patientDto.getClinicId());
        Patient patient = modelMapper.map(patientDto, Patient.class);
        patient.setClinic(clinic);
        Patient saved = patientRepository.save(patient);
        log.info("AUDIT patient.created clinicId={} patientId={}", clinic.getId(), saved.getId());
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<PatientDto> getAllPatients(Long clinicId) {
        return patientRepository.findByClinicId(clinicId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PatientDto getPatientById(Long clinicId, Long id) {
        Patient patient = patientRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id + " for clinic: " + clinicId));
        return mapToDto(patient);
    }

    @Transactional(readOnly = true)
    public PatientProfileDto getPatientProfile(Long clinicId, Long id) {
        Patient patient = patientRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id + " for clinic: " + clinicId));

        List<AppointmentDto> appointments = appointmentRepository
                .findByPatientIdAndClinicIdOrderByAppointmentDateDescAppointmentTimeDesc(id, clinicId)
                .stream()
                .map(appointmentService::mapToDto)
                .toList();

        List<InvoiceDto> invoices = invoiceRepository
                .findByPatientIdAndClinicIdOrderByInvoiceDateDesc(id, clinicId)
                .stream()
                .map(invoiceService::mapToDto)
                .toList();

        PatientProfileDto profile = new PatientProfileDto();
        profile.setPatient(mapToDto(patient));
        profile.setLastVisitDate(appointmentRepository.findLastVisitDate(id, clinicId));
        profile.setTotalVisits((long) appointments.size());
        profile.setTotalInvoices((long) invoices.size());
        profile.setTotalBilledAmount(safeAmount(invoiceRepository.sumTotalAmountByPatientAndClinic(id, clinicId)));
        profile.setAppointments(appointments);
        profile.setInvoices(invoices);
        return profile;
    }

    @Transactional
    public PatientDto updatePatient(Long clinicId, Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id + " for clinic: " + clinicId));

        patient.setClinic(getClinic(clinicId));
        patient.setName(patientDto.getName());
        patient.setGender(patientDto.getGender());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setEmail(patientDto.getEmail());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setAddress(patientDto.getAddress());
        patient.setBloodGroupType(patientDto.getBloodGroupType());
        patient.setPatientType(patientDto.getPatientType());

        Patient saved = patientRepository.save(patient);
        log.info("AUDIT patient.updated clinicId={} patientId={}", clinicId, saved.getId());
        return mapToDto(saved);
    }

    @Transactional
    public void deletePatient(Long clinicId, Long id) {
        Patient patient = patientRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id + " for clinic: " + clinicId));
        patientRepository.delete(patient);
        log.info("AUDIT patient.deleted clinicId={} patientId={}", clinicId, id);
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private PatientDto mapToDto(Patient patient) {
        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        patientDto.setClinicId(patient.getClinic().getId());
        patientDto.setCreatedAt(patient.getCreatedAt());
        return patientDto;
    }

    private BigDecimal safeAmount(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }
}
