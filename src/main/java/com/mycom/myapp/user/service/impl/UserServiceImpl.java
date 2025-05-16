package com.mycom.myapp.user.service.impl;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.user.dto.UserInfo;
import com.mycom.myapp.user.dto.request.ChangePasswordRequestDto;
import com.mycom.myapp.user.dto.request.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.response.UserProfileResponseDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.service.UserService;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Value("${app.default-profile-img}")
	private String defaultProfileImg;

	@Value("${app.upload-dir}")
	private String uploadRootDir;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public ResponseWithStatus<Void> registerUser(UserRegisterRequestDto request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			return ResponseWithStatus.conflict(CommonResponse.fail("이미 사용 중인 이메일입니다."));
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setProfileImg(defaultProfileImg);

		userRepository.save(user);

		return ResponseWithStatus.ok(CommonResponse.success("회원가입 성공"));
	}

	@Override
	public ResponseWithStatus<Void> checkEmailDuplicate(String email) {
		if (userRepository.findByEmail(email).isPresent()) {
			return ResponseWithStatus.conflict(CommonResponse.fail("이미 사용 중인 이메일입니다."));
		}

		return ResponseWithStatus.ok(CommonResponse.success("사용 가능한 이메일입니다."));
	}

	@Override
	public ResponseWithStatus<Void> changePassword(ChangePasswordRequestDto request, UserInfo currentUser) {
		Optional<User> userOpt = userRepository.findById(currentUser.getId());
		if (userOpt.isEmpty()) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("사용자를 찾을 수 없습니다."));
		}

		User user = userOpt.get();

		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("현재 비밀번호가 일치하지 않습니다."));
		}

		if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("새 비밀번호는 현재 비밀번호와 다르게 입력해주세요."));
		}

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);

		return ResponseWithStatus.ok(CommonResponse.success("비밀번호 변경 성공"));
	}

	@Override
	public ResponseWithStatus<UserProfileResponseDto> getUserProfile(int id) {
		Optional<User> userOpt = userRepository.findById(id);
		if(userOpt.isEmpty()) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("사용자를 찾을 수 없습니다."));
		}

		User user = userOpt.get();

		UserProfileResponseDto profile = UserProfileResponseDto.from(user);

		return ResponseWithStatus.ok(CommonResponse.success(profile,"프로필 조회 성공"));
	}

	@Override
	public ResponseWithStatus<Void> updateUserName(int id, String name) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("사용자를 찾을 수 없습니다."));
		}

		User user = userOpt.get();

		if (!StringUtils.hasText(name)) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("이름을 입력해주세요."));
		}

		user.setName(name);
		userRepository.save(user); // JPA 변경 감지 (영속성) → save() 없어도 OK, 필요 시 명시적 save()로 의도 표현 가능

		return ResponseWithStatus.ok(CommonResponse.success("이름 변경 성공"));
	}

	@Override
	public ResponseWithStatus<UserProfileResponseDto> updateProfileImage(int id,
			MultipartFile profileImage) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("사용자를 찾을 수 없습니다."));
		}

		if (profileImage == null || profileImage.isEmpty()) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("프로필 이미지를 선택해주세요."));
		}

		User user = userOpt.get();

		String profileDir = uploadRootDir + "/profile/";
		String fileName = "profile_" + user.getId() + "_" + System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
		File dest = new File(profileDir + fileName);
		dest.getParentFile().mkdirs();

		try {
			profileImage.transferTo(dest);
		} catch (IOException e) {
			return ResponseWithStatus.badRequest(CommonResponse.fail("프로필 이미지 업로드 실패"));
		}

		String imagePath = "/uploads/profile/" + fileName;
		user.setProfileImg(imagePath);
		userRepository.save(user);

		UserProfileResponseDto profile = UserProfileResponseDto.from(user);

		return ResponseWithStatus.ok(CommonResponse.success(profile, "프로필 이미지 변경 성공"));
	}
}


