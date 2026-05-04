package com.studiomedico.mydoctor.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequestDTO {
    @NotBlank(message = "Il nome è obbligatorio")
    private String firstName;

    @NotBlank(message = "il cognome è obbligatorio")
    private String lastName;

    @NotBlank(message = "L'email è obbligatoria")
    @Email
    private String email;

    private String phoneNumber;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    private String fiscalCode;
}
