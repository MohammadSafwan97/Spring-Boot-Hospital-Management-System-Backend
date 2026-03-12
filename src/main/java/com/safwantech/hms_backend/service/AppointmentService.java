package com.safwantech.hms_backend.service;
import com.safwantech.hms_backend.dto.AppointmentDto;
import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.entity.type.AppointmentStatus;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {

        Doctor doctor = doctorRepository.findById(appointmentDto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: "
                                + appointmentDto.getDoctorId()));

        Patient patient = patientRepository.findById(appointmentDto.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: "
                                + appointmentDto.getPatientId()));

        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentDto response = modelMapper.map(savedAppointment, AppointmentDto.class);

        response.setDoctorId(doctor.getId());
        response.setPatientId(patient.getId());

        return response;
    }
    public List<AppointmentDto> getAllAppointments(){
        List<AppointmentDto> appointments=appointmentRepository.findAll().stream()
                .map(appointment->modelMapper
                        .map(appointment,AppointmentDto.class))
                .toList();
        return appointments;
    }

    public AppointmentDto getById(Long appointmentId){
        Appointment appointment=appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new ResourceNotFoundException
                        ("Appointment not find with id :"+appointmentId));
                return modelMapper.map(appointment,AppointmentDto.class);
    }

    @Transactional
    public AppointmentDto updateAppoitnment(Long appointmentId,AppointmentDto dto){
        Patient patient=patientRepository.findById(dto.getPatientId())
                .orElseThrow(()->new ResourceNotFoundException
                        ("Patient not found with id: "+dto.getPatientId()));

        Doctor doctor=doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(()->new ResourceNotFoundException
                        ("Doctor not found with id: "+dto.getDoctorId()));

        Appointment appointment=appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new ResourceNotFoundException
                        ("Appointment not found with id: "+dto.getId()));

        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(dto.getStatus());
        appointment.setRemarks(dto.getRemarks());
        Appointment savedAppointment=appointmentRepository.save(appointment);

        AppointmentDto updatedDto=modelMapper.map(savedAppointment,AppointmentDto.class);
        updatedDto.setPatientId(savedAppointment.getPatient().getId());
        updatedDto.setDoctorId(savedAppointment.getDoctor().getId());
        return updatedDto;
    }


    @Transactional
    public void deleteAppointment(Long appointmentId){

        if(!appointmentRepository.existsById(appointmentId)){
            throw new ResourceNotFoundException(
                    "Appointment not found with id: " + appointmentId);
        }

        appointmentRepository.deleteById(appointmentId);
    }



}




