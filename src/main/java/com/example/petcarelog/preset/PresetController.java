package com.example.petcarelog.preset;

import com.example.petcarelog.user.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/presets")
public class PresetController {

    private final PresetService presetService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public PresetResponse create(
            Authentication authentication,
            @Valid @RequestBody PresetCreateRequest request
    ) {
        Long userId = currentUserService.getCurrentUserId(authentication);
        return presetService.create(userId, request);
    }

    @GetMapping
    public List<PresetResponse> findAll(
            Authentication authentication,
            @RequestParam(required = false) String category
    ) {
        Long userId = currentUserService.getCurrentUserId(authentication);
        return presetService.findAll(userId, category);
    }

    @PutMapping("/tracking")
    public void updateTracking(
            Authentication authentication,
            @Valid @RequestBody TrackingUpdateRequest request
    ) {
        Long userId = currentUserService.getCurrentUserId(authentication);
        presetService.updateTracking(userId, request.presetIds());
    }

    @DeleteMapping("/{presetId}")
    public void delete(@PathVariable Long presetId) {
        presetService.delete(presetId);
    }

    @GetMapping("/{presetId}")
    public PresetResponse findOne(@PathVariable Long presetId) {
        return presetService.findOne(presetId);
    }

    @PutMapping("/{presetId}")
    public PresetResponse update(
            Authentication authentication,
            @PathVariable Long presetId,
            @Valid @RequestBody PresetCreateRequest request
    ) {
        Long userId = currentUserService.getCurrentUserId(authentication);
        return presetService.update(userId, presetId, request);
    }
}