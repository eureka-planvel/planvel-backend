package com.mycom.myapp.auth.controller;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        try {
            boolean loginSuccess = authService.login(loginRequestDto, session);
            return ResponseEntity.ok(loginSuccess);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(false);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(true);
    }
}
