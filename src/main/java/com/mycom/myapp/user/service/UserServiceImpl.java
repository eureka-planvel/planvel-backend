package com.mycom.myapp.user.service;

import java.util.Optional;

import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserRegisterResponseDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	
	@Override
	public UserRegisterResponseDto insertUser(UserRegisterRequestDto userRegisterRequestDto) {
	    Optional<User> existingUser = userRepository.findByEmail(userRegisterRequestDto.getEmail());
	    if (existingUser.isPresent()) {
	        throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
	    }

	    User user = new User();
	    user.setName(userRegisterRequestDto.getName());
	    user.setEmail(userRegisterRequestDto.getEmail());

	    String encodedPassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());
	    user.setPassword(encodedPassword);

	    User savedUser = userRepository.save(user);

	    return UserRegisterResponseDto.builder()
	        .id(savedUser.getId())
	        .name(savedUser.getName())
	        .email(savedUser.getEmail())
	        .profileImg(savedUser.getProfileImg())
	        .build();
	}
}


