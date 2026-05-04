package com.studiomedico.mydoctor.dto;

import com.studiomedico.mydoctor.entity.Appointment;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorRequestDTO {
    @NotBlank(message = "Il nome è obbligatorio")
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String lastName;

    @NotBlank(message = "La specializzazione è obbligatoria")
    private String specialization;

    @NotBlank(message = "La email è obbligatoria")
    @Email
    private String email;
}
