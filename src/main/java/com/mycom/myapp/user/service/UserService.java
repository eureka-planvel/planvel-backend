package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.UserRegisterRequestDto;

public interface UserService {
	boolean insertUser(UserRegisterRequestDto userRegisterRequestDto);
	boolean isEmailDuplicate(String email);
}
