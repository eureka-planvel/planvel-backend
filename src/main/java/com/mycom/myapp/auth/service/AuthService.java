package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    boolean login(LoginRequestDto loginRequestDto, HttpSession session);
}
