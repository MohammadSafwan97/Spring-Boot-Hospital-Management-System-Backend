package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.DoctorDto;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DoctorDto createDoctor(DoctorDto doctorDto) {

        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return modelMapper.map(savedDoctor, DoctorDto.class);
    }

    @Transactional(readOnly = true)
    public List<DoctorDto> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public DoctorDto updateDoctor(Long doctorId, DoctorDto doctorDto) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        doctor.setName(doctorDto.getName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setExperience(doctorDto.getExperience());
        doctor.setPhoneNo(doctorDto.getPhoneNo());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return modelMapper.map(updatedDoctor, DoctorDto.class);
    }

    @Transactional(readOnly = true)
    public DoctorDto getById(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        return modelMapper.map(doctor, DoctorDto.class);
    }

    public void deleteDoctor(Long doctorId){
        Doctor doctor=doctorRepository.findById(doctorId)
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found with id: " + doctorId));
        doctorRepository.delete(doctor);
    }

}


