package com.safwantech.hms_backend.repository;

import com.safwantech.hms_backend.entity.Appointment;
import com.safwantech.hms_backend.entity.type.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
List<Appointment> findByDoctorId(Long doctorId);
List<Appointment> findByPatientId(Long patientId);
List<Appointment> findByClinicId(Long clinicId);
List<Appointment> findByPatientIdAndClinicIdOrderByAppointmentDateDescAppointmentTimeDesc(Long patientId, Long clinicId);
Optional<Appointment> findByIdAndClinicId(Long id, Long clinicId);

    Long countByAppointmentDate(LocalDate now);
    Long countByClinicIdAndAppointmentDate(Long clinicId, LocalDate now);
    Long countByStatus(AppointmentStatus status);
    Long countByClinicIdAndStatus(Long clinicId, AppointmentStatus status);
    Long countByClinicIdAndStatusAndAppointmentDate(Long clinicId, AppointmentStatus status, LocalDate appointmentDate);

    @Query("select max(a.appointmentDate) from Appointment a where a.patient.id = :patientId and a.clinic.id = :clinicId and a.status in (com.safwantech.hms_backend.entity.type.AppointmentStatus.CHECKED_IN, com.safwantech.hms_backend.entity.type.AppointmentStatus.COMPLETED)")
    LocalDate findLastVisitDate(Long patientId, Long clinicId);
}
