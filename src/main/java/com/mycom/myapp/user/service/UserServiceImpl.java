package com.mycom.myapp.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserRegisterRequestDto;
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

	@Override
	public boolean isEmailDuplicate(String email) {
		return userRepository.findByEmail(email).isPresent();
	}
}


