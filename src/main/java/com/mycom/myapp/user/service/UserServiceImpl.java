package com.mycom.myapp.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserRegisterDto;
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
	public UserRegisterResponseDto insertUser(UserRegisterDto userRegisterDto) {
	    Optional<User> existingUser = userRepository.findByEmail(userRegisterDto.getEmail());
	    if (existingUser.isPresent()) {
	        throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
	    }

	    User user = new User();
	    user.setName(userRegisterDto.getName());
	    user.setEmail(userRegisterDto.getEmail());

	    String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword()); 
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


