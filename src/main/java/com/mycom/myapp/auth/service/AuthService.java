package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.common.response.ResponseWithStatus;
import jakarta.servlet.http.HttpSession;

public interface AuthService {

    ResponseWithStatus<Void> login(LoginRequestDto loginRequestDto, HttpSession session);
}
