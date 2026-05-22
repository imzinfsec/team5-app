package com.example.petcarelog.carelog;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "care_logs")
public class CareLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "preset_id")
    private Long presetId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "care_name", nullable = false, length = 50)
    private String careName;

    @Column(nullable = false, length = 30)
    private String category;

    @Column(nullable = false, length = 50)
    private String icon;

    @Column(nullable = false, length = 30)
    private String color;

    @Column(length = 255)
    private String memo;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public CareLog(Long petId, Long presetId, Long userId, String careName, String category, String icon, String color, String memo, LocalDateTime recordedAt) {
        this.petId = petId;
        this.presetId = presetId;
        this.userId = userId;
        this.careName = careName;
        this.category = category;
        this.icon = icon;
        this.color = color;
        this.memo = memo;
        this.recordedAt = recordedAt;
    }

    public void update(String memo, LocalDateTime recordedAt) {
        this.memo = memo;
        this.recordedAt = recordedAt;
    }
}