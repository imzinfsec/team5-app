package com.example.petcarelog.preset;

import com.example.petcarelog.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "care_presets")
public class CarePreset extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String category;

    @Column(nullable = false, length = 50)
    private String icon;

    @Column(nullable = false, length = 30)
    private String color;

    @Column(name = "is_default", nullable = false)
    private Boolean defaultPreset;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    public CarePreset(Long userId, String name, String category, String icon, String color, Boolean defaultPreset, Integer sortOrder) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.icon = icon;
        this.color = color;
        this.defaultPreset = defaultPreset;
        this.active = true;
        this.sortOrder = sortOrder;
    }

    public void update(String name, String category, String icon, String color, Integer sortOrder) {
        this.name = name;
        this.category = category;
        this.icon = icon;
        this.color = color;
        this.sortOrder = sortOrder;
    }

    public void deactivate() {
        this.active = false;
    }
}