package org.example.nuiifo0d.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.nuiifo0d.dto.request.LoginUser;
import org.example.nuiifo0d.dto.request.RegisterUser;
import org.example.nuiifo0d.dto.response.ApiResponse;
import org.example.nuiifo0d.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    ResponseEntity<ApiResponse<?>> register(@RequestBody @Valid RegisterUser request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("login")
    ResponseEntity<ApiResponse<?>> login(@RequestBody @Valid LoginUser request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("logout")
    ResponseEntity<ApiResponse<?>> logout() {
        return ResponseEntity.ok(authService.logout());
    }
}
