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

        // FOOD
        presetRepository.save(new CarePreset(null, "자연식", "FOOD", "FOOD_BOWL", "#6f9ed8", true, 1));
        presetRepository.save(new CarePreset(null, "사료", "FOOD", "FEED", "#6f9ed8", true, 2));
        presetRepository.save(new CarePreset(null, "물", "FOOD", "WATER_DROP", "#6f9ed8", true, 3));
        presetRepository.save(new CarePreset(null, "유산균", "FOOD", "SUPPLEMENT", "#6f9ed8", true, 4));
        presetRepository.save(new CarePreset(null, "오메가3", "FOOD", "OMEGA3", "#6f9ed8", true, 5));

        // HEALTH
        presetRepository.save(new CarePreset(null, "몸무게측정", "HEALTH", "WEIGHT", "#8ebd69", true, 1));
        presetRepository.save(new CarePreset(null, "주사", "HEALTH", "INJECTION", "#8ebd69", true, 2));
        presetRepository.save(new CarePreset(null, "구충제", "HEALTH", "PILL", "#8ebd69", true, 3));
        presetRepository.save(new CarePreset(null, "연고", "HEALTH", "OINTMENT", "#8ebd69", true, 4));
        presetRepository.save(new CarePreset(null, "혈액검사", "HEALTH", "BLOOD_TEST", "#8ebd69", true, 5));

        // ACTIVITY
        presetRepository.save(new CarePreset(null, "산책", "ACTIVITY", "WALK", "#9168ca", true, 1));
        presetRepository.save(new CarePreset(null, "산책1시간", "ACTIVITY", "LONG_WALK", "#9168ca", true, 2));
        presetRepository.save(new CarePreset(null, "놀이", "ACTIVITY", "PLAY", "#9168ca", true, 3));
        presetRepository.save(new CarePreset(null, "등산", "ACTIVITY", "HIKING", "#9168ca", true, 4));
        presetRepository.save(new CarePreset(null, "친구와놀기", "ACTIVITY", "FRIENDS", "#9168ca", true, 5));

        // GROOMING
        presetRepository.save(new CarePreset(null, "양치", "GROOMING", "TOOTH", "#de8fa2", true, 1));
        presetRepository.save(new CarePreset(null, "미용", "GROOMING", "GROOMING", "#de8fa2", true, 2));
        presetRepository.save(new CarePreset(null, "목욕", "GROOMING", "BATH", "#de8fa2", true, 3));
        presetRepository.save(new CarePreset(null, "귀청소", "GROOMING", "EAR_CLEANING", "#de8fa2", true, 4));
        presetRepository.save(new CarePreset(null, "발톱", "GROOMING", "NAIL", "#de8fa2", true, 5));

        // POTTY
        presetRepository.save(new CarePreset(null, "배변", "POTTY", "POOP", "#dcb85a", true, 1));
        presetRepository.save(new CarePreset(null, "소변", "POTTY", "PEE", "#dcb85a", true, 2));

        // SYMPTOM
        presetRepository.save(new CarePreset(null, "구토", "SYMPTOM", "VOMIT", "#a77964", true, 1));
        presetRepository.save(new CarePreset(null, "설사", "SYMPTOM", "DIARRHEA", "#a77964", true, 2));
        presetRepository.save(new CarePreset(null, "기침", "SYMPTOM", "COUGH", "#a77964", true, 3));
        presetRepository.save(new CarePreset(null, "가려움", "SYMPTOM", "ITCHING", "#a77964", true, 4));
    }
}