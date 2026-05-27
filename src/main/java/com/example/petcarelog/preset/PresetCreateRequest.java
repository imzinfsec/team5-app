package com.example.petcarelog.preset;

import jakarta.validation.constraints.NotBlank;

public record PresetCreateRequest(
        @NotBlank String name,
        @NotBlank String category,
        @NotBlank String icon,
        @NotBlank String color,
        Integer sortOrder
) {
}