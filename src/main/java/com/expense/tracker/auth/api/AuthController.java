package com.expense.tracker.auth.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/api/hello")
    public String hello(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        return "Hello, " + email;
    }
}
