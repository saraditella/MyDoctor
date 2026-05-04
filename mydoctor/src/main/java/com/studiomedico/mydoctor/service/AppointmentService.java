package com.studiomedico.mydoctor.service;

import com.studiomedico.mydoctor.dto.AppointmentRequestDTO;
import com.studiomedico.mydoctor.dto.AppointmentResponseDTO;
import com.studiomedico.mydoctor.entity.Appointment;
import com.studiomedico.mydoctor.entity.Doctor;
import com.studiomedico.mydoctor.entity.Patient;
import com.studiomedico.mydoctor.enums.AppointmentStatus;
import com.studiomedico.mydoctor.enums.VisitType;
import com.studiomedico.mydoctor.exception.ResourceNotFoundException;
import com.studiomedico.mydoctor.repository.AppointmentRepository;
import com.studiomedico.mydoctor.repository.DoctorRepository;
import com.studiomedico.mydoctor.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        //mmi prendo paziente e dottore
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paziente inesistente con id: " + dto.getPatientId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Medico inseistente con id: " + dto.getDoctorId()));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(LocalDate.from(dto.getAppointmentDate()));
        appointment.setAppointmentTime(LocalTime.from(dto.getAppointmentDate()));
        appointment.setVisitType(dto.getVisitType());
        appointment.setNotes(dto.getNotes());
        appointment.setStatus(dto.getStatus());

        Appointment salvato = appointmentRepository.save(appointment);
        return mapToResponseDTO(salvato);
    }


    @Transactional
    public AppointmentResponseDTO getAppointmentById(UUID id) {
        return appointmentRepository.findById(id).map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Appuntamento non trovato con ID: " + id));
    }

    @Transactional
    public List<AppointmentResponseDTO> getAppointments(LocalDate date, AppointmentStatus status, VisitType type, UUID patientId, UUID doctorId) {
        return appointmentRepository.findAppointmentsByFilters(date, status, type, patientId, doctorId).stream().map(this::mapToResponseDTO).toList();
    }


    @Transactional
    public List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Paziente inesistente con questo id: " + patientId);
        }
        return appointmentRepository.findByPatientId(patientId).stream().map(this::mapToResponseDTO).toList();
    }

    @Transactional
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Medico non trovato con l'id: " + doctorId);
        }
        return appointmentRepository.findByDoctorId(doctorId).stream().map(this::mapToResponseDTO).toList();
    }


    @Transactional
    public AppointmentResponseDTO updateAppointment(UUID id, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appuntamento inesistente con id: " + id));

        //controllo se esistono paziente e medico senno non li posso aggiornare
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paziente inesistente"));
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Medico inesistente"));

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dto.getAppointmentDate().toLocalDate());
        appointment.setAppointmentTime(dto.getAppointmentDate().toLocalTime());
        appointment.setVisitType(dto.getVisitType());
        appointment.setNotes(dto.getNotes());

        return mapToResponseDTO(appointmentRepository.save(appointment));
    }

    @Transactional
    public void deleteAppointment(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossibile eliminare perché non è stato trovato l'appuntamento");
        }
        appointmentRepository.deleteById(id);
    }

    private AppointmentResponseDTO mapToResponseDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName("Dottore/Dottoressa " + appointment.getDoctor().getLastName());
        dto.setAppointmentDate(LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime()));
        dto.setStatus(appointment.getStatus());
        dto.setVisitType(appointment.getVisitType());
        dto.setNotes(appointment.getNotes());
        return dto;
    }
}