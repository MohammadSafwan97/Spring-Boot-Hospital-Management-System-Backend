package com.safwantech.hms_backend.service;


import com.safwantech.hms_backend.dto.PatientDto;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;



    /* ---------------- CREATE ---------------- */

    public PatientDto createPatient(PatientDto patientDto) {

        Patient patient = modelMapper.map(patientDto, Patient.class);

        Patient savedPatient = patientRepository.save(patient);

        return modelMapper.map(savedPatient, PatientDto.class);
    }

    /* -------------------GET ALL ------------------*/

    public List<PatientDto> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .collect(Collectors.toList());
    }

    /* ---------------- GET BY ID ---------------- */

    public PatientDto getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        return modelMapper.map(patient, PatientDto.class);
    }

    /* ---------------- UPDATE ---------------- */

    public PatientDto updatePatient(Long id, PatientDto patientDto) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        patient.setPatientId(patientDto.getPatientId());
        patient.setName(patientDto.getName());
        patient.setAge(patientDto.getAge());
        patient.setGender(patientDto.getGender());
        patient.setPhone(patientDto.getPhone());
        patient.setBloodGroup(patientDto.getBloodGroup());
        patient.setPatientType(patientDto.getPatientType());
        patient.setWard(patientDto.getWard());
        patient.setDoctorAssigned(patientDto.getDoctorAssigned());

        Patient updatedPatient = patientRepository.save(patient);

        return modelMapper.map(updatedPatient, PatientDto.class);
    }

    /* ---------------- DELETE ---------------- */

    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        patientRepository.delete(patient);
    }

}