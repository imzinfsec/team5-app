package com.example.petcarelog.carelog;

import java.time.LocalDateTime;

public record CareLogUpdateRequest(
        String memo,
        LocalDateTime recordedAt
) {
}
