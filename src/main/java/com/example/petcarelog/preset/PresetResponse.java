package com.example.petcarelog.preset;

public record PresetResponse(
        Long id,
        Long userId,
        String name,
        String category,
        String icon,
        String color,
        Boolean defaultPreset,
        Boolean active,
        Integer sortOrder,
        Boolean tracked
) {
    public static PresetResponse from(CarePreset preset, Boolean tracked) {
        return new PresetResponse(
                preset.getId(),
                preset.getUserId(),
                preset.getName(),
                preset.getCategory(),
                preset.getIcon(),
                preset.getColor(),
                preset.getDefaultPreset(),
                preset.getActive(),
                preset.getSortOrder(),
                tracked
        );
    }

    public static PresetResponse from(CarePreset preset) {
        return from(preset, false);
    }
}