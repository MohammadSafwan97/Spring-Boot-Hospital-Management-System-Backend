package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PatientDto;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    /* ---------------- CREATE ---------------- */

    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {

        Patient patient = modelMapper.map(patientDto, Patient.class);

        Patient savedPatient = patientRepository.save(patient);

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    /* ---------------- GET ALL ---------------- */

    @Transactional(readOnly = true)
    public List<PatientDto> getAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .collect(Collectors.toList());
    }

    /* ---------------- GET BY ID ---------------- */

    @Transactional(readOnly = true)
    public PatientDto getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        return modelMapper.map(patient, PatientDto.class);
    }

    /* ---------------- UPDATE ---------------- */

    @Transactional
    public PatientDto updatePatient(Long id, PatientDto patientDto) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));


        patient.setName(patientDto.getName());
        patient.setGender(patientDto.getGender());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setEmail(patientDto.getEmail());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setAddress(patientDto.getAddress());
        patient.setBloodGroupType(patientDto.getBloodGroupType());
        patient.setPatientType(patientDto.getPatientType());

        Patient updatedPatient = patientRepository.save(patient);

        return modelMapper.map(updatedPatient, PatientDto.class);
    }

    /* ---------------- DELETE ---------------- */

    @Transactional
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        patientRepository.delete(patient);
    }

}
