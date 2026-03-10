package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.DoctorDto;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public DoctorDto createDoctor(DoctorDto doctorDto){
        Doctor doctor=modelMapper.map(doctorDto,Doctor.class);
        Doctor savedDoctor=doctorRepository.save(doctor);
        return modelMapper.map(savedDoctor,DoctorDto.class);
    }
    public List<DoctorDto> getAllDoctors(){
        return doctorRepository.findAll()
                .stream()
                .map(doctor->modelMapper.map(doctor,DoctorDto.class))
                .collect(Collectors.toList());
    }
}
