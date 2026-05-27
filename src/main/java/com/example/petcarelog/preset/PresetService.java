package com.example.petcarelog.preset;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PresetService {

    private final PresetRepository presetRepository;
    private final UserPresetSettingRepository userPresetSettingRepository;

    @Transactional
    public PresetResponse create(Long userId, PresetCreateRequest request) {
        CarePreset preset = new CarePreset(
                userId,
                request.name(),
                request.category(),
                request.icon(),
                request.color(),
                false,
                request.sortOrder() == null ? 0 : request.sortOrder()
        );

        CarePreset savedPreset = presetRepository.save(preset);

        UserPresetSetting setting = new UserPresetSetting(
                userId,
                savedPreset.getId(),
                false
        );

        userPresetSettingRepository.save(setting);

        return PresetResponse.from(savedPreset, false);
    }

    @Transactional(readOnly = true)
    public List<PresetResponse> findAll(Long userId, String category) {
        List<CarePreset> presets;

        if (category == null || category.isBlank()) {
            presets = presetRepository.findAvailablePresets(userId);
        } else {
            presets = presetRepository.findAvailablePresetsByCategory(userId, category);
        }

        Set<Long> trackedPresetIds = new HashSet<>(
                userPresetSettingRepository.findByUserIdAndTrackedTrue(userId)
                        .stream()
                        .map(UserPresetSetting::getPresetId)
                        .toList()
        );

        return presets.stream()
                .map(preset -> PresetResponse.from(
                        preset,
                        trackedPresetIds.contains(preset.getId())
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public PresetResponse findOne(Long presetId) {
        CarePreset preset = presetRepository.findById(presetId)
                .orElseThrow(() -> new IllegalArgumentException("프리셋을 찾을 수 없습니다. presetId=" + presetId));

        return PresetResponse.from(preset);
    }

    @Transactional
    public PresetResponse update(Long userId, Long presetId, PresetCreateRequest request) {
        CarePreset preset = presetRepository.findById(presetId)
                .orElseThrow(() -> new IllegalArgumentException("프리셋을 찾을 수 없습니다. presetId=" + presetId));

        preset.update(
                request.name(),
                request.category(),
                request.icon(),
                request.color(),
                request.sortOrder() == null ? preset.getSortOrder() : request.sortOrder()
        );

        Boolean tracked = userPresetSettingRepository
                .findByUserIdAndPresetId(userId, presetId)
                .map(UserPresetSetting::getTracked)
                .orElse(false);

        return PresetResponse.from(preset, tracked);
    }

    @Transactional
    public void updateTracking(Long userId, List<Long> presetIds) {
        List<CarePreset> availablePresets = presetRepository.findAvailablePresets(userId);
        Set<Long> selectedPresetIds = new HashSet<>(presetIds);

        for (CarePreset preset : availablePresets) {
            UserPresetSetting setting = userPresetSettingRepository
                    .findByUserIdAndPresetId(userId, preset.getId())
                    .orElseGet(() -> userPresetSettingRepository.save(
                            new UserPresetSetting(userId, preset.getId(), false)
                    ));

            setting.updateTracked(selectedPresetIds.contains(preset.getId()));
        }
    }

    @Transactional
    public void delete(Long presetId) {
        CarePreset preset = presetRepository.findById(presetId)
                .orElseThrow(() -> new IllegalArgumentException("프리셋을 찾을 수 없습니다. presetId=" + presetId));

        preset.deactivate();
    }
}