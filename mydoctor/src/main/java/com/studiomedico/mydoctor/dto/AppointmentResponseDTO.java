package com.studiomedico.mydoctor.dto;


import com.studiomedico.mydoctor.enums.AppointmentStatus;
import com.studiomedico.mydoctor.enums.VisitType;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import java.util.UUID;


@Setter
@Getter
public class AppointmentResponseDTO {
    private UUID id;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private VisitType visitType;
    private String notes;
}
