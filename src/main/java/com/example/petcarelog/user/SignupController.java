package com.example.petcarelog.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class SignupController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@Valid SignupRequest request) {
        userService.signup(request);
        return "redirect:/login.html?signup=true";
    }
}