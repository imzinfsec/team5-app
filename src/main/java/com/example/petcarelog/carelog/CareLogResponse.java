package com.example.petcarelog.carelog;

import java.time.LocalDateTime;

public record CareLogResponse(
        Long id,
        Long petId,
        Long presetId,
        Long userId,
        String careName,
        String category,
        String icon,
        String color,
        String memo,
        LocalDateTime recordedAt
) {
    public static CareLogResponse from(CareLog careLog) {
        return new CareLogResponse(
                careLog.getId(),
                careLog.getPetId(),
                careLog.getPresetId(),
                careLog.getUserId(),
                careLog.getCareName(),
                careLog.getCategory(),
                careLog.getIcon(),
                careLog.getColor(),
                careLog.getMemo(),
                careLog.getRecordedAt()
        );
    }
}