package com.example.petcarelog.pet;

import com.example.petcarelog.user.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{petId}")
    public void delete(@PathVariable Long petId) {
        petService.delete(petId);
    }
}