package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.dto.response.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
