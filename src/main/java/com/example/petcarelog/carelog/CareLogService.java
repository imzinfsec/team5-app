package com.example.petcarelog.carelog;

import com.example.petcarelog.pet.PetRepository;
import com.example.petcarelog.preset.CarePreset;
import com.example.petcarelog.preset.PresetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CareLogService {

    private final CareLogRepository careLogRepository;
    private final PetRepository petRepository;
    private final PresetRepository presetRepository;

    @Transactional
    public CareLogResponse createQuick(Long userId, Long petId, CareLogQuickCreateRequest request) {
        petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("반려동물을 찾을 수 없습니다. petId=" + petId));

        CarePreset preset = presetRepository.findById(request.presetId())
                .orElseThrow(() -> new IllegalArgumentException("프리셋을 찾을 수 없습니다. presetId=" + request.presetId()));

        LocalDateTime recordedAt = request.recordedAt() == null
                ? LocalDateTime.now()
                : request.recordedAt();

        CareLog careLog = new CareLog(
                petId,
                preset.getId(),
                userId,
                preset.getName(),
                preset.getCategory(),
                preset.getIcon(),
                preset.getColor(),
                null,
                recordedAt
        );

        CareLog savedCareLog = careLogRepository.save(careLog);
        return CareLogResponse.from(savedCareLog);
    }
    
    @Transactional(readOnly = true)
    public List<CareLogResponse> findByDate(Long petId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return careLogRepository.findByPetIdAndRecordedAtBetweenOrderByRecordedAtAsc(petId, start, end)
                .stream()
                .map(CareLogResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CareLogResponse findOne(Long careLogId) {
        CareLog careLog = careLogRepository.findById(careLogId)
                .orElseThrow(() -> new IllegalArgumentException("돌봄 기록을 찾을 수 없습니다. careLogId=" + careLogId));

        return CareLogResponse.from(careLog);
    }

    @Transactional
    public CareLogResponse update(Long careLogId, CareLogUpdateRequest request) {
        CareLog careLog = careLogRepository.findById(careLogId)
                .orElseThrow(() -> new IllegalArgumentException("돌봄 기록을 찾을 수 없습니다. careLogId=" + careLogId));

        LocalDateTime recordedAt = request.recordedAt() == null
                ? careLog.getRecordedAt()
                : request.recordedAt();

        careLog.update(request.memo(), recordedAt);

        return CareLogResponse.from(careLog);
    }

    @Transactional
    public void delete(Long careLogId) {
        if (!careLogRepository.existsById(careLogId)) {
            throw new IllegalArgumentException("돌봄 기록을 찾을 수 없습니다. careLogId=" + careLogId);
        }

        careLogRepository.deleteById(careLogId);
    }
    
    @Transactional(readOnly = true)
    public List<LocalDate> findRecordedDates(Long petId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return careLogRepository.findRecordedDates(
                petId,
                startDate.atStartOfDay(),
                endDate.atStartOfDay()
        );
    }
}