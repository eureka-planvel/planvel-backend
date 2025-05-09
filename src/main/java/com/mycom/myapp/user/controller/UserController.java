package com.mycom.myapp.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.UserRegisterResponseDto;
import com.mycom.myapp.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> insertUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) { 
		try {
			UserRegisterResponseDto savedUser = userService.insertUser(userRegisterRequestDto);
			return ResponseEntity.status(201).body(savedUser);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("회원가입 중 오류 발생");
		}
	}
	
	@GetMapping("/email-check")
	public ResponseEntity<?> checkEmailDuplicate(@RequestParam("email") String email) {
	    boolean isDuplicate = userService.isEmailDuplicate(email);
	    if (isDuplicate) {
	        return ResponseEntity.status(409).body("이미 사용 중인 이메일입니다.");
	    } else {
	        return ResponseEntity.ok("사용 가능한 이메일입니다.");
	    }
	}

}
