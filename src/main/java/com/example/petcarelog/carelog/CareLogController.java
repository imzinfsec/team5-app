package com.example.petcarelog.carelog;

import com.example.petcarelog.user.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CareLogController {

    private final CareLogService careLogService;
    private final CurrentUserService currentUserService;

    @PostMapping("/api/pets/{petId}/care-logs/quick")
    public CareLogResponse createQuick(
            Authentication authentication,
            @PathVariable Long petId,
            @Valid @RequestBody CareLogQuickCreateRequest request
    ) {
        Long userId = currentUserService.getCurrentUserId(authentication);
        return careLogService.createQuick(userId, petId, request);
    }

    @GetMapping("/api/pets/{petId}/care-logs")
    public List<CareLogResponse> findByDate(
            @PathVariable Long petId,
            @RequestParam LocalDate date
    ) {
        return careLogService.findByDate(petId, date);
    }

    @GetMapping("/api/care-logs/{careLogId}")
    public CareLogResponse findOne(@PathVariable Long careLogId) {
        return careLogService.findOne(careLogId);
    }

    @PutMapping("/api/care-logs/{careLogId}")
    public CareLogResponse update(
            @PathVariable Long careLogId,
            @RequestBody CareLogUpdateRequest request
    ) {
        return careLogService.update(careLogId, request);
    }

    @DeleteMapping("/api/care-logs/{careLogId}")
    public ResponseEntity<Void> delete(@PathVariable Long careLogId) {
        careLogService.delete(careLogId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/pets/{petId}/care-logs/dates")
    public List<LocalDate> findRecordedDates(
            @PathVariable Long petId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return careLogService.findRecordedDates(petId, year, month);
    }
}