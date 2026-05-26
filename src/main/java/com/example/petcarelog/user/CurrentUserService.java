package com.example.petcarelog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public Long getCurrentUserId(Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("로그인 사용자를 찾을 수 없습니다. email=" + email));

        return user.getId();
    }
}