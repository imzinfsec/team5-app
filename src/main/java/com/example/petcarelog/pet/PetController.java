package com.example.petcarelog.pet;

import com.example.petcarelog.user.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public PetResponse create(
            Authentication authentication,
            @Valid @RequestBody PetCreateRequest request
    ) {
        Long userId = currentUserService.getCurrentUserId(authentication);
        return petService.create(userId, request);
    }

    @GetMapping
    public List<PetResponse> findAll(Authentication authentication) {
        Long userId = currentUserService.getCurrentUserId(authentication);
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
    
    // 이미지 조회 API 추가
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<byte[]> findImage(@PathVariable String filename) {
        PetImageService.PetImageFile image = petService.findImage(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                .contentType(MediaType.parseMediaType(image.contentType()))
                .body(image.bytes());
    }
}