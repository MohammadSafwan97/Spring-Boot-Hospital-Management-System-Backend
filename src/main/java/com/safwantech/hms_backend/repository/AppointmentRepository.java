package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.entity.type.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
List<Appointment> findByDoctorId(Long doctorId);
List<Appointment> findByPatientId(Long patientId);
List<Appointment> findByClinicId(Long clinicId);
Optional<Appointment> findByIdAndClinicId(Long id, Long clinicId);

    Long countByAppointmentDate(LocalDate now);
    Long countByClinicIdAndAppointmentDate(Long clinicId, LocalDate now);
    Long countByStatus(AppointmentStatus status);
    Long countByClinicIdAndStatus(Long clinicId, AppointmentStatus status);
}
