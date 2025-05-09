package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.UserRegisterResponseDto;

public interface UserService {
	UserRegisterResponseDto insertUser(UserRegisterRequestDto userRegisterRequestDto);
}
