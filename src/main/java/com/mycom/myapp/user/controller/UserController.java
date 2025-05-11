package com.mycom.myapp.user.controller;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.dto.UserProfileResponseDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

