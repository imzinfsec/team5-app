package com.example.petcarelog.preset;

import com.example.petcarelog.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_preset_settings")
public class UserPresetSetting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "preset_id", nullable = false)
    private Long presetId;

    @Column(name = "is_tracked", nullable = false)
    private Boolean tracked;

    public UserPresetSetting(Long userId, Long presetId, Boolean tracked) {
        this.userId = userId;
        this.presetId = presetId;
        this.tracked = tracked;
    }

    public void updateTracked(Boolean tracked) {
        this.tracked = tracked;
    }
}