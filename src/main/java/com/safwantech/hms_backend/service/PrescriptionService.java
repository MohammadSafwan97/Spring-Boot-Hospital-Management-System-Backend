package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PrescriptionDto;
import com.safwantech.hms_backend.entity.Prescription;
import com.safwantech.hms_backend.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final ModelMapper modelMapper;
    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto ){
        Prescription prescription= modelMapper.map(prescriptionDto,Prescription.class);
        Prescription savedPrescription=prescriptionRepository.save(prescription);
        return modelMapper.map(savedPrescription,PrescriptionDto.class);
    }

}
