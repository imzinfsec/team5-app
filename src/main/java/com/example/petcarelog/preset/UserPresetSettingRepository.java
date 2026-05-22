package com.example.petcarelog.preset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPresetSettingRepository extends JpaRepository<UserPresetSetting, Long> {

    Optional<UserPresetSetting> findByUserIdAndPresetId(Long userId, Long presetId);

    List<UserPresetSetting> findByUserIdAndTrackedTrue(Long userId);

    void deleteByUserId(Long userId);
}