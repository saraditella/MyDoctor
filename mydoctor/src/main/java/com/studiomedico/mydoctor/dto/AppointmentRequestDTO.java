package com.studiomedico.mydoctor.dto;

import com.studiomedico.mydoctor.enums.AppointmentStatus;
import com.studiomedico.mydoctor.enums.VisitType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequestDTO {

    @NotNull(message = "L'ID del paziente è obbligatorio")
    private UUID patientId;

    @NotNull(message = "L'ID del medico è obbligatorio")
    private UUID doctorId;

    @NotNull(message = "La data dell'appuntamento è obbligatoria")
    @Future(message = "L'appuntamento non deve essere passato oppure il giorno stesso")
    private LocalDateTime appointmentDate;

    @NotNull(message = "La data dell'appuntamento è obbligatoria")
    private LocalTime appointmentTime;

    @NotNull(message = "Il tipo di visita è obbligatorio")
    private VisitType visitType;

    @NotNull(message = "Lo stato è obbligatorio")
    private AppointmentStatus status;

    private String notes;
}
