package com.safwantech.hms_backend.service;

import com.safwantech.hms_backend.dto.PrescriptionDto;
import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.entity.Clinic;
import com.safwantech.hms_backend.entity.Doctor;
import com.safwantech.hms_backend.entity.Patient;
import com.safwantech.hms_backend.entity.Prescription;
import com.safwantech.hms_backend.entity.PrescriptionItem;
import com.safwantech.hms_backend.exception.ResourceNotFoundException;
import com.safwantech.hms_backend.repository.AppointmentRepository;
import com.safwantech.hms_backend.repository.ClinicRepository;
import com.safwantech.hms_backend.repository.DoctorRepository;
import com.safwantech.hms_backend.repository.PatientRepository;
import com.safwantech.hms_backend.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicRepository clinicRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PrescriptionDto createPrescription(PrescriptionDto dto) {
        Clinic clinic = getClinic(dto.getClinicId());
        Doctor doctor = doctorRepository.findByIdAndClinicId(dto.getDoctorId(), clinic.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id : " + dto.getDoctorId() + " for clinic: " + clinic.getId()));
        Patient patient = patientRepository.findByIdAndClinicId(dto.getPatientId(), clinic.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id : " + dto.getPatientId() + " for clinic: " + clinic.getId()));
        Appointment appointment = appointmentRepository.findByIdAndClinicId(dto.getAppointmentId(), clinic.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id : " + dto.getAppointmentId() + " for clinic: " + clinic.getId()));

        Prescription prescription = new Prescription();
        prescription.setClinic(clinic);
        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setMedication(dto.getMedication());
        prescription.setDosage(dto.getDosage());
        prescription.setInstruction(dto.getInstruction());
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setAppointment(appointment);

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<PrescriptionItem> items = dto.getItems().stream()
                    .map(itemDto -> {
                        PrescriptionItem item = new PrescriptionItem();
                        item.setClinic(clinic);
                        item.setMedicine(itemDto.getMedicine());
                        item.setDosage(itemDto.getDosage());
                        item.setInstruction(itemDto.getInstruction());
                        item.setPrescription(prescription);
                        return item;
                    })
                    .toList();
            prescription.getPrescriptionItems().addAll(items);
        }

        return mapToDto(prescriptionRepository.save(prescription));
    }

    @Transactional
    public PrescriptionDto getPrescriptionById(Long clinicId, Long id) {
        Prescription prescription = prescriptionRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id : " + id + " for clinic: " + clinicId));
        return mapToDto(prescription);
    }

    @Transactional
    public List<PrescriptionDto> getAllPrescriptions(Long clinicId) {
        return prescriptionRepository.findByClinicId(clinicId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public PrescriptionDto updatePrescription(Long clinicId, Long id, PrescriptionDto dto) {
        Prescription prescription = prescriptionRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id : " + id + " for clinic: " + clinicId));

        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setMedication(dto.getMedication());
        prescription.setDosage(dto.getDosage());
        prescription.setInstruction(dto.getInstruction());

        return mapToDto(prescriptionRepository.save(prescription));
    }

    @Transactional
    public void deletePrescription(Long clinicId, Long id) {
        Prescription prescription = prescriptionRepository.findByIdAndClinicId(id, clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found with id : " + id + " for clinic: " + clinicId));
        prescriptionRepository.delete(prescription);
    }

    private Clinic getClinic(Long clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found with id: " + clinicId));
    }

    private PrescriptionDto mapToDto(Prescription prescription) {
        PrescriptionDto prescriptionDto = modelMapper.map(prescription, PrescriptionDto.class);
        prescriptionDto.setClinicId(prescription.getClinic().getId());
        prescriptionDto.setAppointmentId(prescription.getAppointment().getId());
        prescriptionDto.setPatientId(prescription.getPatient().getId());
        prescriptionDto.setDoctorId(prescription.getDoctor().getId());
        return prescriptionDto;
    }
}
