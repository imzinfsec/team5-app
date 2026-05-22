package com.example.petcarelog.preset;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_preset_settings")
public class UserPresetSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "preset_id", nullable = false)
    private Long presetId;

    @Column(name = "is_tracked", nullable = false)
    private Boolean tracked;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public UserPresetSetting(Long userId, Long presetId, Boolean tracked) {
        this.userId = userId;
        this.presetId = presetId;
        this.tracked = tracked;
    }

    public void updateTracked(Boolean tracked) {
        this.tracked = tracked;
    }
}