package com.mycom.myapp.auth.controller;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "인증 API", description = "로그인 및 로그아웃 처리")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "아이디와 비밀번호를 이용해 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호 불일치")
    })
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        try {
            boolean loginSuccess = authService.login(loginRequestDto, session);
            return ResponseEntity.ok(loginSuccess);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(false);
        }
    }

    @Operation(summary = "로그아웃", description = "현재 로그인한 사용자의 세션을 종료합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(true);
    }
}
