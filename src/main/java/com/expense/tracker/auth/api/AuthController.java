package com.expense.tracker.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/auth/success")
    public String authSuccess() {
        return "Login Successful!";
    }
}
