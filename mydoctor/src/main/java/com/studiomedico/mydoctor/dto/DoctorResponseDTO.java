package com.studiomedico.mydoctor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DoctorResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
}
