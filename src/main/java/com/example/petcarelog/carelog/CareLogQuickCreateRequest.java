package com.example.petcarelog.carelog;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CareLogQuickCreateRequest(
        @NotNull Long presetId,
        LocalDateTime recordedAt
) {
}