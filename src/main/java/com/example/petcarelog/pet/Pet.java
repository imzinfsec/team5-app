package com.example.petcarelog.pet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 30)
    private String species;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "image_url", length = 500)
    private String imageUrl; 
    
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Pet(Long userId, String name, String species, LocalDate birthDate) {
        this.userId = userId;
        this.name = name;
        this.species = species;
        this.birthDate = birthDate;
    }

    public void update(String name, String species, LocalDate birthDate) {
        this.name = name;
        this.species = species;
        this.birthDate = birthDate;
    }

     // 이미지 URL 업데이트 메서드 추가
    public void updateImageUrl(String imageUrl) {   // ← 추가
        this.imageUrl = imageUrl;
    }
}
