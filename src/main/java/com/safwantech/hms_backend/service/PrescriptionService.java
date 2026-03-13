package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PrescriptionDto;
import com.safwantech.hms_backend.entity.*;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import com.safwantech.hms_backend.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PrescriptionDto createPrescription(PrescriptionDto dto) {
        Doctor doctor=doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found with id : "+dto.getDoctorId()));

        Patient patient=patientRepository.findById(dto.getPatientId())
                .orElseThrow(()->new ResourceNotFoundException("Patient not found with id :"+dto.getPatientId()));

        Appointment appointment=appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(()->new ResourceNotFoundException("Appointment not found with id"+dto.getAppointmentId()));

        Prescription prescription=new Prescription();

        prescription.setDosage(dto.getDosage());
        prescription.setMedication(dto.getMedication());
        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setInstruction(dto.getInstruction());

        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setAppointment(appointment);

        if (dto.getItems() != null) {

            List<PrescriptionItem> items = dto.getItems()
                    .stream()
                    .map(itemDto -> {

                        PrescriptionItem item = new PrescriptionItem();

                        item.setMedicine(itemDto.getMedicine());
                        item.setDosage(itemDto.getDosage());
                        item.setInstruction(itemDto.getInstruction());

                        /**
                         * Important:
                         * Set parent reference for JPA relationship
                         */
                        item.setPrescription(prescription);

                        return item;

                    }).collect(Collectors.toList());

            prescription.setPrescriptionItems(items);
        }


        prescriptionRepository.save(prescription);

        return modelMapper.map(prescription,PrescriptionDto.class);
    }



    public PrescriptionDto getPrescriptionById(Long id) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id : " + id));

        return modelMapper.map(prescription, PrescriptionDto.class);
    }

    public List<PrescriptionDto> getAllPrescriptions() {

        List<Prescription> prescriptions = prescriptionRepository.findAll();

        return prescriptions.stream()
                .map(p -> modelMapper.map(p, PrescriptionDto.class))
                .toList();
    }


    @Transactional
    public PrescriptionDto updatePrescription(Long id, PrescriptionDto dto) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id : " + id));


        /**
         * Update fields
         */
        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setMedication(dto.getMedication());
        prescription.setDosage(dto.getDosage());
        prescription.setInstruction(dto.getInstruction());


        /**
         * Save updated entity
         */
        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        return modelMapper.map(updatedPrescription, PrescriptionDto.class);
    }

    @Transactional
    public void deletePrescription(Long id) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id : " + id));

        prescriptionRepository.delete(prescription);
    }




}
