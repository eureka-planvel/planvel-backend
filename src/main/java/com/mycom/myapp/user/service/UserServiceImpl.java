package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.ChangePasswordRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public boolean insertUser(UserRegisterRequestDto userRegisterRequestDto) {
	    
		if (userRepository.findByEmail(userRegisterRequestDto.getEmail()).isPresent()) {
	        return false;
	    }
		
	    User user = new User();
	    
	    user.setName(userRegisterRequestDto.getName());
	    user.setEmail(userRegisterRequestDto.getEmail());
	    String encodedPassword = passwordEncoder.encode(userRegisterRequestDto.getPassword());
	    user.setPassword(encodedPassword);
	    if (userRegisterRequestDto.getProfileImg() == null) {
	        user.setProfileImg("noProfile.png");
	    }
	    userRepository.save(user);

	    return true;
	}

	@Override
	public boolean isEmailDuplicate(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	@Override
	public void changePassword(int userId, ChangePasswordRequestDto requestDto) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		if(!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
			throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
		}

		user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
		userRepository.save(user);
	}
}


