package com.studiomedico.mydoctor.service;

import com.studiomedico.mydoctor.dto.AppointmentRequestDTO;
import com.studiomedico.mydoctor.dto.AppointmentResponseDTO;
import com.studiomedico.mydoctor.dto.DoctorRequestDTO;
import com.studiomedico.mydoctor.dto.DoctorResponseDTO;
import com.studiomedico.mydoctor.entity.Appointment;
import com.studiomedico.mydoctor.entity.Doctor;
import com.studiomedico.mydoctor.entity.Patient;
import com.studiomedico.mydoctor.exception.ResourceNotFoundException;
import com.studiomedico.mydoctor.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Transactional
    public DoctorResponseDTO createDoctor(DoctorRequestDTO dto){
        if(doctorRepository.existsByEmail(dto.getEmail())) {
            throw new DataIntegrityViolationException("La email " + dto.getEmail() + " è gia presente nel database");
        }
        Doctor doctor = mapToEntity(dto);
        doctorRepository.save(doctor);
        return mapToResponseDTO(doctor);
    }

    @Transactional
    public List<DoctorResponseDTO> getDoctors(String firstName, String lastName, String specialization, String email) {
        List<Doctor> doctors = doctorRepository.getDoctorByFilters(firstName, lastName, specialization, email);
        return doctors.stream().map(this::mapToResponseDTO).toList();
    }

    @Transactional
    public DoctorResponseDTO getDoctorById(UUID id) {
        return doctorRepository.findById(id).map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Nessun dottore corrisponde a questo id: "+ id));
    }

    @Transactional
    public DoctorResponseDTO updateDoctor(UUID id, DoctorRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impossibile aggiornare perché nessun dottore corrisponde a questo id " + id));
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setEmail(dto.getEmail());
        doctor.setSpecialization(dto.getSpecialization());

        Doctor update = doctorRepository.save(doctor);
        return mapToResponseDTO(update);
    }

    @Transactional
    public void deleteDoctor(UUID id) {
        if(!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossibile eliminare il dottore con id " + id + " perche non trovato");
        }
        doctorRepository.deleteById(id);
    }


    //me li trasformo in dto e entity
    private Doctor mapToEntity(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setEmail(dto.getEmail());
        doctor.setSpecialization(dto.getSpecialization());

        return doctor;
    }

    private DoctorResponseDTO mapToResponseDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setEmail(doctor.getEmail());
        dto.setSpecialization(doctor.getSpecialization());

        return dto;
    }
}
