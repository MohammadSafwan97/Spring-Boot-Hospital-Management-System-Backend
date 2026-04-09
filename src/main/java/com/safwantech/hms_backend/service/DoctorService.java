package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.DoctorDto;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        doctor.setClinic(getClinic(doctorDto.getClinicId()));
        return mapToDto(doctorRepository.save(doctor));
    }

    @Transactional(readOnly = true)
    public List<DoctorDto> getAllDoctors(Long clinicId) {
        return doctorRepository.findByClinicId(clinicId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public DoctorDto updateDoctor(Long clinicId, Long doctorId, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findByIdAndClinicId(doctorId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId + " for clinic: " + clinicId));

        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setExperience(doctorDto.getExperience());
        doctor.setPhoneNo(doctorDto.getPhoneNo());
        doctor.setEmail(doctorDto.getEmail());

        return mapToDto(doctorRepository.save(doctor));
    }

    @Transactional(readOnly = true)
    public DoctorDto getById(Long clinicId, Long doctorId) {
        Doctor doctor = doctorRepository.findByIdAndClinicId(doctorId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId + " for clinic: " + clinicId));
        return mapToDto(doctor);
    }

    @Transactional
    public void deleteDoctor(Long clinicId, Long doctorId) {
        Doctor doctor = doctorRepository.findByIdAndClinicId(doctorId, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId + " for clinic: " + clinicId));
        doctorRepository.delete(doctor);
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private DoctorDto mapToDto(Doctor doctor) {
        DoctorDto doctorDto = modelMapper.map(doctor, DoctorDto.class);
        doctorDto.setClinicId(doctor.getClinic().getId());
        return doctorDto;
    }
}


