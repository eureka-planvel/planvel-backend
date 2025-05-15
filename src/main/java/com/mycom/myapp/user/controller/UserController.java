package com.mycom.myapp.user.controller;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.dto.ChangePasswordRequestDto;
import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.dto.UserProfileResponseDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mycom.myapp.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "회원 관련 API")
public class UserController {
	private final UserService userService;

	@Operation(summary = "회원가입", description = "사용자 정보를 받아 회원가입을 수행합니다.")
	@PostMapping("/register")
	public ResponseEntity<Boolean> insertUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
	    try {
	        boolean result = userService.insertUser(userRegisterRequestDto);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(false);
	    }
	}

	@Operation(summary = "이메일 중복 체크", description = "이메일 중복 여부를 확인합니다.")
	@GetMapping("/email-check")
	public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam("email") String email) {
	    boolean isDuplicate = userService.isEmailDuplicate(email);
	    return ResponseEntity.ok(!isDuplicate);
	}

	@Operation(summary = "비밀번호 변경", description = "현재 비밀번호와 새 비밀번호를 받아 비밀번호를 변경합니다.")
	@PutMapping("/password")
	public ResponseEntity<Boolean> changePassword(
			@RequestBody ChangePasswordRequestDto requestDto,
			Authentication authentication) {

		if(authentication == null || !authentication.isAuthenticated() ||
		authentication.getPrincipal() instanceof String) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}

		LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

		userService.changePassword(requestDto, loginUser);
		return ResponseEntity.ok(true);
	}

	@Operation(summary = "내 프로필 조회", description = "로그인한 사용자의 프로필 정보를 조회합니다.")
	@GetMapping("/profile")
	public ResponseEntity<UserProfileResponseDto> getUserProfile(Authentication authentication) {
		if(authentication == null || !authentication.isAuthenticated() ||
				authentication.getPrincipal() instanceof String) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

		try {
			UserProfileResponseDto profile = userService.getUserProfile(loginUser);
			return ResponseEntity.ok(profile);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@Operation(summary = "프로필 수정", description = "이름과 프로필 이미지를 수정합니다.")
	@PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<UserProfileResponseDto> updateUserProfile(
			Authentication authentication,
			@RequestPart(value = "name", required = false) String name,
			@RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {

		if(authentication == null || !authentication.isAuthenticated() ||
				authentication.getPrincipal() instanceof String) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

		try {
			UserProfileResponseDto updatedProfile = userService.updateUserProfile(loginUser, name, profileImage);
			return ResponseEntity.ok(updatedProfile);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}

