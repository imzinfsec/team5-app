package com.example.petcarelog.preset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PresetCreateRequest(
        @NotNull Long userId,
        @NotBlank String name,
        @NotBlank String category,
        @NotBlank String icon,
        @NotBlank String color,
        Integer sortOrder
) {
}