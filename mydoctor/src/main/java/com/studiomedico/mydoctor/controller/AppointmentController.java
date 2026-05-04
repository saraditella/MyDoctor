package com.studiomedico.mydoctor.controller;

import com.studiomedico.mydoctor.dto.AppointmentRequestDTO;
import com.studiomedico.mydoctor.dto.AppointmentResponseDTO;
import com.studiomedico.mydoctor.enums.AppointmentStatus;
import com.studiomedico.mydoctor.enums.VisitType;
import com.studiomedico.mydoctor.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
        return new ResponseEntity<>(appointmentService.createAppointment(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate, @RequestParam(required = false) VisitType visitType, @RequestParam(required = false) AppointmentStatus status, @RequestParam(required = false) UUID doctorId, @RequestParam(required = false) UUID patientId) {
        return ResponseEntity.ok(appointmentService.getAppointments(appointmentDate, status, visitType, doctorId, patientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointmentById(@PathVariable UUID id, @Valid @RequestBody AppointmentRequestDTO dto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}