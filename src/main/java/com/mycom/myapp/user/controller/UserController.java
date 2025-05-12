package com.mycom.myapp.user.controller;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.dto.ChangePasswordRequestDto;
import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.dto.UserProfileResponseDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.mycom.myapp.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Boolean> insertUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
	    try {
	        boolean result = userService.insertUser(userRegisterRequestDto);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(false);
	    }
	}

	@GetMapping("/email-check")
	public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam("email") String email) {
	    boolean isDuplicate = userService.isEmailDuplicate(email);
	    return ResponseEntity.ok(!isDuplicate);
	}

	@PutMapping("/password")
	public ResponseEntity<Boolean> changePassword(
			@RequestBody ChangePasswordRequestDto requestDto,
			Authentication authentication) {

		if(authentication == null || !authentication.isAuthenticated() ||
		authentication.getPrincipal() instanceof String) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}

		LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

		userService.changePassword(loginUser.getId(), requestDto);
		return ResponseEntity.ok(true);
	}

	@GetMapping("/profile")
	public ResponseEntity<UserProfileResponseDto> getUserProfile(HttpSession session) {
		LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		try {
			UserProfileResponseDto profile = userService.getUserProfileById(loginUser.getId());
			return ResponseEntity.ok(profile);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}

