package com.example.petcarelog.carelog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {

    List<CareLog> findByPetIdAndRecordedAtBetweenOrderByRecordedAtAsc(
            Long petId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query(value = """
        SELECT DISTINCT DATE(recorded_at)
        FROM care_logs
        WHERE pet_id = :petId
          AND recorded_at >= :start
          AND recorded_at < :end
        ORDER BY DATE(recorded_at)
    """, nativeQuery = true)
    List<LocalDate> findRecordedDates(
            @Param("petId") Long petId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}