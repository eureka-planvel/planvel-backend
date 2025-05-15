package com.mycom.myapp.user.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.request.ChangePasswordRequestDto;
import com.mycom.myapp.user.dto.request.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.response.UserProfileResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	ResponseWithStatus<Void> registerUser(UserRegisterRequestDto userRegisterRequestDto);

	ResponseWithStatus<Void> checkEmailDuplicate(String email);

	ResponseWithStatus<Void> changePassword(ChangePasswordRequestDto request, UserDto user);

	ResponseWithStatus<UserProfileResponseDto> getUserProfile(int id);

	ResponseWithStatus<Void> updateUserName(int id, String name);

	ResponseWithStatus<UserProfileResponseDto> updateProfileImage(int id, MultipartFile profileImage);
}