package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.ChangePasswordRequestDto;
import com.mycom.myapp.user.dto.UserProfileResponseDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;

public interface UserService {
	boolean insertUser(UserRegisterRequestDto userRegisterRequestDto);
	boolean isEmailDuplicate(String email);
	UserProfileResponseDto getUserProfileById(int userId);

	void changePassword(int userId, ChangePasswordRequestDto requestDto);
}
