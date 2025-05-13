package com.mycom.myapp.user.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.dto.ChangePasswordRequestDto;
import com.mycom.myapp.user.dto.UserProfileResponseDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	boolean insertUser(UserRegisterRequestDto userRegisterRequestDto);

	boolean isEmailDuplicate(String email);

	UserProfileResponseDto getUserProfile();

	void changePassword(ChangePasswordRequestDto requestDto, LoginResponseDto loginUser);

	UserProfileResponseDto updateUserProfile(String name, MultipartFile profileImage);
}