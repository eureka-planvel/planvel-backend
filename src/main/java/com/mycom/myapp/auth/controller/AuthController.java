package com.mycom.myapp.auth.controller;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.service.AuthService;
import com.mycom.myapp.common.resolver.LoginUser;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.user.dto.UserInfo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        ResponseWithStatus<Void> response = authService.login(loginRequestDto, session);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(@LoginUser UserInfo user, HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(CommonResponse.success("로그아웃 성공"));
    }
}
