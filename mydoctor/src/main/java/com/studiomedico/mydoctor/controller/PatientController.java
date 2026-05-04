package com.studiomedico.mydoctor.controller;

import com.studiomedico.mydoctor.dto.AppointmentResponseDTO;
import com.studiomedico.mydoctor.dto.PatientRequestDTO;
import com.studiomedico.mydoctor.dto.PatientResponseDTO;
import com.studiomedico.mydoctor.service.AppointmentService;
import com.studiomedico.mydoctor.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO response = patientService.createPatient(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String email, @RequestParam(required = false) String fiscalCode) {
        return ResponseEntity.ok(patientService.getPatients(firstName, lastName, email, fiscalCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable UUID id) {
        PatientResponseDTO responseDTO = patientService.getPatientById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatientById(@PathVariable UUID id, @Valid @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO response = patientService.updatePatient(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getPatientAppointments(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(id));
    }
}
