package com.example.petcarelog.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PetService {

    private final PetRepository petRepository;

    @Transactional
    public PetResponse create(PetCreateRequest request) {
        Pet pet = new Pet(
                request.userId(),
                request.name(),
                request.species(),
                request.birthDate()
        );

        Pet savedPet = petRepository.save(pet);
        return PetResponse.from(savedPet);
    }

    @Transactional(readOnly = true)
    public List<PetResponse> findAll(Long userId) {
        return petRepository.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(PetResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PetResponse findOne(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("반려동물을 찾을 수 없습니다. petId=" + petId));

        return PetResponse.from(pet);
    }

    @Transactional
    public PetResponse update(Long petId, PetCreateRequest request) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("반려동물을 찾을 수 없습니다. petId=" + petId));

        pet.update(request.name(), request.species(), request.birthDate());

        return PetResponse.from(pet);
    }

    @Transactional
    public void delete(Long petId) {
        petRepository.deleteById(petId);
    }
}
