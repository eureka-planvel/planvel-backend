package com.mycom.myapp.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.user.dto.UserRegisterDto;
import com.mycom.myapp.user.dto.UserRegisterResponseDto;
import com.mycom.myapp.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> insertUser(@RequestBody UserRegisterDto userRegisterDto) { 
		try {
			UserRegisterResponseDto savedUser = userService.insertUser(userRegisterDto);
			return ResponseEntity.status(201).body(savedUser);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(409).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body("회원가입 중 오류 발생");
		}
	}
	

}
