package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PatientDto;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        Clinic clinic = getClinic(patientDto.getClinicId());
        Patient patient = modelMapper.map(patientDto, Patient.class);
        patient.setClinic(clinic);
        return mapToDto(patientRepository.save(patient));
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

        return mapToDto(patientRepository.save(patient));
    }

    @Transactional
    public void deletePatient(Long clinicId, Long id) {
        Patient patient = patientRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id + " for clinic: " + clinicId));
        patientRepository.delete(patient);
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private PatientDto mapToDto(Patient patient) {
        PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
        patientDto.setClinicId(patient.getClinic().getId());
        return patientDto;
    }
}
