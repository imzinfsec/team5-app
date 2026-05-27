package com.example.petcarelog.global;

import com.example.petcarelog.preset.CarePreset;
import com.example.petcarelog.preset.PresetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final PresetRepository presetRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (presetRepository.existsByDefaultPresetTrue()) {
            return;
        }

        presetRepository.save(new CarePreset(null, "사료", "FEED", "🍚", "#FFB74D", true, 1));
        presetRepository.save(new CarePreset(null, "물", "FEED", "💧", "#64B5F6", true, 2));
        presetRepository.save(new CarePreset(null, "간식", "FEED", "🦴", "#A1887F", true, 3));

        presetRepository.save(new CarePreset(null, "산책", "ACTIVITY", "🐾", "#81C784", true, 1));
        presetRepository.save(new CarePreset(null, "놀이", "ACTIVITY", "🎾", "#4DB6AC", true, 2));

        presetRepository.save(new CarePreset(null, "목욕", "CARE", "🛁", "#4FC3F7", true, 1));
        presetRepository.save(new CarePreset(null, "빗질", "CARE", "🪮", "#BA68C8", true, 2));
        presetRepository.save(new CarePreset(null, "발톱 정리", "CARE", "✂️", "#90A4AE", true, 3));

        presetRepository.save(new CarePreset(null, "약 복용", "HEALTH", "💊", "#E57373", true, 1));
        presetRepository.save(new CarePreset(null, "병원 방문", "HEALTH", "🏥", "#F06292", true, 2));
    }
}