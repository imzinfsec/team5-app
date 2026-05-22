package com.example.petcarelog.preset;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/presets")
public class PresetController {

    private final PresetService presetService;

    @PostMapping
    public PresetResponse create(@Valid @RequestBody PresetCreateRequest request) {
        return presetService.create(request);
    }

    @GetMapping
    public List<PresetResponse> findAll(
            @RequestParam Long userId,
            @RequestParam(required = false) String category
    ) {
        return presetService.findAll(userId, category);
    }

    @PutMapping("/tracking")
    public void updateTracking(@Valid @RequestBody TrackingUpdateRequest request) {
        presetService.updateTracking(request.userId(), request.presetIds());
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
            @PathVariable Long presetId,
            @Valid @RequestBody PresetCreateRequest request
    ) {
        return presetService.update(presetId, request);
    }
}