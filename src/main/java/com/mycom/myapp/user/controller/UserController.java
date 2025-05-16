package com.mycom.myapp.user.controller;

import com.mycom.myapp.common.resolver.LoginUser;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.user.dto.request.ChangePasswordRequestDto;
import com.mycom.myapp.user.dto.UserInfo;
import com.mycom.myapp.user.dto.request.UpdateNameRequest;
import com.mycom.myapp.user.dto.response.UserProfileResponseDto;
import com.mycom.myapp.user.dto.request.UserRegisterRequestDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mycom.myapp.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<CommonResponse<Void>> registerUser(@RequestBody UserRegisterRequestDto request) {

		ResponseWithStatus<Void> response = userService.registerUser(request);
		return ResponseEntity.status(response.getStatus()).body(response.getBody());
	}

	@GetMapping("/email-check")
	public ResponseEntity<CommonResponse<Void>> checkEmailDuplicate(@RequestParam("email") String email) {
		ResponseWithStatus<Void> response = userService.checkEmailDuplicate(email);
		return ResponseEntity.status(response.getStatus()).body(response.getBody());
	}

	@PutMapping("/password")
	public ResponseEntity<CommonResponse<Void>> changePassword( @RequestBody ChangePasswordRequestDto request, @LoginUser UserInfo user) {
		ResponseWithStatus<Void> response = userService.changePassword(request, user);
		return ResponseEntity.status(response.getStatus()).body(response.getBody());
	}

	@GetMapping("/profile")
	public ResponseEntity<CommonResponse<UserProfileResponseDto>> getUserProfile(@LoginUser UserInfo user) {
		ResponseWithStatus<UserProfileResponseDto> response = userService.getUserProfile(user.getId());
		return ResponseEntity.status(response.getStatus()).body(response.getBody());
	}


	@PutMapping("/profile/name")
	public ResponseEntity<CommonResponse<Void>> updateName(@LoginUser UserInfo user, @RequestBody UpdateNameRequest request) {
		ResponseWithStatus<Void> response = userService.updateUserName(user.getId(), request.getName());
		return ResponseEntity.status(response.getStatus()).body(response.getBody());
	}

	@PutMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CommonResponse<UserProfileResponseDto>> updateProfileImage(
			@LoginUser UserInfo user,
			@RequestPart("profileImage") MultipartFile profileImage) {

		ResponseWithStatus<UserProfileResponseDto> response = userService.updateProfileImage(user.getId(), profileImage);
		return ResponseEntity.status(response.getStatus()).body(response.getBody());
	}

}

