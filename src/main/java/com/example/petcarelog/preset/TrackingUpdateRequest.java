package com.example.petcarelog.preset;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TrackingUpdateRequest(
        @NotNull List<Long> presetIds
) {
}