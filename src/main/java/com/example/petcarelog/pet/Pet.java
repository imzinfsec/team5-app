package com.example.petcarelog.pet;

import com.example.petcarelog.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseTimeEntity {

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

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}