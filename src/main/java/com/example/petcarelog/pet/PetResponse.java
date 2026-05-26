package com.example.petcarelog.pet;

import java.time.LocalDate;

public record PetResponse(
        Long id,
        Long userId,
        String name,
        String species,
        LocalDate birthDate,
        String imageUrl
) {
    public static PetResponse from(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getUserId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBirthDate(),
                pet.getImageUrl()
        );
    }
}