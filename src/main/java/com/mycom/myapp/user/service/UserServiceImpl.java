package com.mycom.myapp.user.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.dto.UserProfileResponseDto;
import com.mycom.myapp.user.dto.ChangePasswordRequestDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
			user.setProfileImg("/uploads/profile/profile_noProfile.png");
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

	@Override
	public UserProfileResponseDto getUserProfile(LoginResponseDto loginUser) {
		User user = userRepository.findById(loginUser.getId())
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		return UserProfileResponseDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.profileImg(user.getProfileImg())
				.build();
	}

	@Override
	public UserProfileResponseDto updateUserProfile(LoginResponseDto loginUser, String name, MultipartFile imageFile) {
		User user = userRepository.findById(loginUser.getId())
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		if (StringUtils.hasText(name)) {
			user.setName(name);
		}

		if (imageFile != null && !imageFile.isEmpty()) {
			String uploadDir = "C:/uploads/profile/";
			String fileName = "profile_" + user.getId() + "_" + System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
			File dest = new File(uploadDir + fileName);
			dest.getParentFile().mkdirs();
			try {
				imageFile.transferTo(dest);
			} catch (IOException e) {
				throw new RuntimeException("프로필 이미지 업로드 실패");
			}

			String imagePath = "/uploads/profile/" + fileName;
			user.setProfileImg(imagePath);
		}

		userRepository.save(user);

		return UserProfileResponseDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.profileImg(user.getProfileImg())
				.build();
	}

}


