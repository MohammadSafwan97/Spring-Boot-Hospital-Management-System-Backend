package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PrescriptionDto;
import com.safwantech.hms_backend.dto.PrescriptionItemDto;
import com.safwantech.hms_backend.entity.*;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import com.safwantech.hms_backend.repository.PrescriptionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public PrescriptionDto createPrescription(PrescriptionDto dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found with id: " + dto.getAppointmentId()));

        Prescription prescription = new Prescription();

        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setMedication(dto.getMedication());
        prescription.setDosage(dto.getDosage());
        prescription.setInstruction(dto.getInstruction());

        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setAppointment(appointment);

        List<PrescriptionItem> items = new ArrayList<>();

        if (dto.getItems() != null) {

            for (PrescriptionItemDto itemDto : dto.getItems()) {

                PrescriptionItem item = new PrescriptionItem();

                item.setMedicine(itemDto.getMedicine());
                item.setDosage(itemDto.getDosage());
                item.setInstruction(itemDto.getInstruction());

                item.setPrescription(prescription);

                items.add(item);
            }
        }

        prescription.setPrescriptionItems(items);

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        return mapToDto(savedPrescription);
    }

    public PrescriptionDto getPrescriptionById(Long id) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id: " + id));

        return mapToDto(prescription);
    }

    public List<PrescriptionDto> getAllPrescriptions() {

        List<Prescription> prescriptions = prescriptionRepository.findAll();

        List<PrescriptionDto> dtoList = new ArrayList<>();

        for (Prescription prescription : prescriptions) {
            dtoList.add(mapToDto(prescription));
        }

        return dtoList;
    }

    @Transactional
    public PrescriptionDto updatePrescription(Long id, PrescriptionDto dto) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id: " + id));

        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setMedication(dto.getMedication());
        prescription.setDosage(dto.getDosage());
        prescription.setInstruction(dto.getInstruction());

        Prescription updated = prescriptionRepository.save(prescription);

        return mapToDto(updated);
    }

    @Transactional
    public void deletePrescription(Long id) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found with id: " + id));

        prescriptionRepository.delete(prescription);
    }

    private PrescriptionDto mapToDto(Prescription prescription) {

        PrescriptionDto dto = new PrescriptionDto();

        dto.setId(prescription.getId());
        dto.setDiagnosis(prescription.getDiagnosis());
        dto.setMedication(prescription.getMedication());
        dto.setDosage(prescription.getDosage());
        dto.setInstruction(prescription.getInstruction());

        dto.setDoctorId(prescription.getDoctor().getId());
        dto.setPatientId(prescription.getPatient().getId());
        dto.setAppointmentId(prescription.getAppointment().getId());

        List<PrescriptionItemDto> items = new ArrayList<>();

        if (prescription.getPrescriptionItems() != null) {

            for (PrescriptionItem item : prescription.getPrescriptionItems()) {

                PrescriptionItemDto itemDto = new PrescriptionItemDto();

                itemDto.setMedicine(item.getMedicine());
                itemDto.setDosage(item.getDosage());
                itemDto.setInstruction(item.getInstruction());

                items.add(itemDto);
            }
        }

        dto.setItems(items);

        return dto;
    }
}