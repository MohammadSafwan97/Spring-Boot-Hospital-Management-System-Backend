package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PatientDto;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository,
                          ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    public PatientDto createPatient(PatientDto request) {

        Patient patient = modelMapper.map(request, Patient.class);

        Patient saved = patientRepository.save(patient);

        return modelMapper.map(saved, PatientDto.class);
    }

}
