package com.example.petcarelog.pet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    @PostMapping
    public PetResponse create(@Valid @RequestBody PetCreateRequest request) {
        return petService.create(request);
    }

    @GetMapping
    public List<PetResponse> findAll(@RequestParam Long userId) {
        return petService.findAll(userId);
    }

    @GetMapping("/{petId}")
    public PetResponse findOne(@PathVariable Long petId) {
        return petService.findOne(petId);
    }

    @PutMapping("/{petId}")
    public PetResponse update(
            @PathVariable Long petId,
            @Valid @RequestBody PetCreateRequest request
    ) {
        return petService.update(petId, request);
    }

    // 이미지 업로드 API 추가
    @PostMapping("/{petId}/image")
    public PetResponse uploadImage(
            @PathVariable Long petId,
            @RequestParam("image") MultipartFile image
    ) {
        return petService.uploadImage(petId, image);
    }

    // 이미지 삭제 API 추가
    @DeleteMapping("/{petId}/image")
    public PetResponse deleteImage(@PathVariable Long petId) {
        return petService.deleteImage(petId);
    }

    @DeleteMapping("/{petId}")
    public void delete(@PathVariable Long petId) {
        petService.delete(petId);
    }
}