package com.example.petcarelog.pet;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PetCreateRequest(
        @NotBlank String name,
        @NotBlank String species,
        LocalDate birthDate
) {
}
