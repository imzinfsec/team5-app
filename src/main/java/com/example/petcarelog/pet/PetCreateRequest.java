package com.example.petcarelog.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PetCreateRequest(
        @NotNull Long userId,
        @NotBlank String name,
        @NotBlank String species,
        LocalDate birthDate
) {
}
