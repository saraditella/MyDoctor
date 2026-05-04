package com.studiomedico.mydoctor.service;

import com.studiomedico.mydoctor.dto.PatientRequestDTO;
import com.studiomedico.mydoctor.dto.PatientResponseDTO;
import com.studiomedico.mydoctor.entity.Patient;
import com.studiomedico.mydoctor.exception.ResourceNotFoundException;
import com.studiomedico.mydoctor.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        if(patientRepository.existsByFiscalCode(dto.getFiscalCode())) {
            throw new DataIntegrityViolationException("Il codice fiscale " + dto.getFiscalCode() + " risulta già nel database");
        }
        Patient patient = mapToEntity(dto);
        patientRepository.save(patient);
        return mapToResponseDTO(patient);
    }

    @Transactional
    public List<PatientResponseDTO> getPatients(String firstName, String lastName, String email, String fiscalCode) {
        List<Patient> patients = patientRepository.findByFilters(firstName, lastName, email, fiscalCode);
        return patients.stream().map(this::mapToResponseDTO).toList();
    }

    @Transactional
    public PatientResponseDTO getPatientById(UUID id) {
        return patientRepository.findById(id).map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Nessun paziente corrisponde a questo id: " + id));
    }

    @Transactional
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impossibile aggiornare: paziente con id " + id + " non trovato"));
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setFiscalCode(dto.getFiscalCode());

       Patient update = patientRepository.save(patient);

        return mapToResponseDTO(update);
    }

    @Transactional
    public void deletePatient(UUID id) {
        if(!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossibile eliminare il paziente con id " + id + "poiché non è presente nel database");
        }
        patientRepository.deleteById(id);
    }

    //mappo in dto e viceversa a parte
    private Patient mapToEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setFiscalCode(dto.getFiscalCode());

        return patient;
    }

    private PatientResponseDTO mapToResponseDTO(Patient patient) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setEmail(patient.getEmail());
        dto.setPhoneNumber(patient.getPhoneNumber());
        dto.setFiscalCode(patient.getFiscalCode());

        return dto;
    }
}
