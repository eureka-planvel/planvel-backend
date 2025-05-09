package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.UserRegisterDto;
import com.mycom.myapp.user.dto.UserRegisterResponseDto;

public interface UserService {
	UserRegisterResponseDto insertUser(UserRegisterDto userRegisterDto);
}
