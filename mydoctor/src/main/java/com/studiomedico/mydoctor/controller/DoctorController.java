package com.studiomedico.mydoctor.controller;

import com.studiomedico.mydoctor.dto.*;
import com.studiomedico.mydoctor.service.AppointmentService;
import com.studiomedico.mydoctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@Valid @RequestBody DoctorRequestDTO dto) {
        DoctorResponseDTO response = doctorService.createDoctor(dto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getPatients(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String specialization, @RequestParam(required = false) String email) {
        List<DoctorResponseDTO> response = doctorService.getDoctors(firstName, lastName, specialization, email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable UUID id) {
        DoctorResponseDTO response= doctorService.getDoctorById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctorById(@PathVariable UUID id, @Valid @RequestBody DoctorRequestDTO dto) {
        DoctorResponseDTO response = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getDoctorAppointments(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(id));
    }
}

