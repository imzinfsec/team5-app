package com.example.petcarelog.user;

import com.example.petcarelog.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String role;

    public static User create(String email, String password, String nickname, String role) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.nickname = nickname;
        user.role = role;
        return user;
    }
}