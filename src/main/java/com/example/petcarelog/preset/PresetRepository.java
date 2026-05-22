package com.example.petcarelog.preset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PresetRepository extends JpaRepository<CarePreset, Long> {

    @Query("""
        select p
        from CarePreset p
        where p.active = true
          and (p.defaultPreset = true or p.userId = :userId)
        order by p.category asc, p.sortOrder asc, p.id asc
    """)
    List<CarePreset> findAvailablePresets(Long userId);

    @Query("""
        select p
        from CarePreset p
        where p.active = true
          and p.category = :category
          and (p.defaultPreset = true or p.userId = :userId)
        order by p.sortOrder asc, p.id asc
    """)
    List<CarePreset> findAvailablePresetsByCategory(Long userId, String category);
}