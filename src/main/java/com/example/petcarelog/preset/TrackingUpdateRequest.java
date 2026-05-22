package com.example.petcarelog.preset;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TrackingUpdateRequest(
        @NotNull Long userId,
        @NotNull List<Long> presetIds
) {
}