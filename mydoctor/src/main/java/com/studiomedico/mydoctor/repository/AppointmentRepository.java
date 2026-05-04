package com.studiomedico.mydoctor.repository;

import com.studiomedico.mydoctor.entity.Appointment;

import com.studiomedico.mydoctor.enums.AppointmentStatus;
import com.studiomedico.mydoctor.enums.VisitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @Query("select a from Appointment a where " +
            "(:appointmentDate is null or a.appointmentDate = :appointmentDate) and " +
            "(:status is null or a.status = :status) and " +
            "(:visitType is null or a.visitType = :visitType) and " +
            "(:doctorId is null or a.doctor.id = :doctorId) and " +
            "(:patientId is null or a.patient.id = :patientId)")
    List<Appointment> findAppointmentsByFilters(@Param("appointmentDate") LocalDate appointmentDate, @Param("status") AppointmentStatus status, @Param("visitType") VisitType visitType, @Param("doctorId") UUID doctorId, @Param("patientId") UUID patientId);

    List<Appointment> findByPatientId(UUID patientId);

    List<Appointment> findByDoctorId(UUID doctorId);

}
