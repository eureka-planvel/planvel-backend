package com.mycom.myapp.user.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.user.dto.UserInfo;
import com.mycom.myapp.user.dto.request.ChangePasswordRequestDto;
import com.mycom.myapp.user.dto.request.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.response.UserProfileResponseDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "defaultProfileImg", "/uploads/profile/default.png");
    }

    @Test
    void registerUser_success() {
        UserRegisterRequestDto request = UserRegisterRequestDto.builder()
                .name("홍길동")
                .email("test@example.com")
                .password("password123")
                .build();

        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());
        given(passwordEncoder.encode(request.getPassword())).willReturn("encodedPassword123");

        ResponseWithStatus<Void> response = userService.registerUser(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("회원가입 성공");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getName()).isEqualTo("홍길동");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword123");
        assertThat(savedUser.getProfileImg()).isEqualTo("/uploads/profile/default.png");
    }

    @Test
    void registerUser_emailAlreadyExists() {
        UserRegisterRequestDto request = UserRegisterRequestDto.builder()
                .name("홍길동")
                .email("test@example.com")
                .password("password123")
                .build();

        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.of(new User()));

        ResponseWithStatus<Void> response = userService.registerUser(request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getMsg()).isEqualTo("이미 사용 중인 이메일입니다.");
        verify(userRepository, never()).save(any());
    }
    @Test
    void checkEmailDuplicate_alreadyExists() {
        String email = "test@example.com";
        given(userRepository.findByEmail(email)).willReturn(Optional.of(new User()));

        ResponseWithStatus<Void> response = userService.checkEmailDuplicate(email);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getMsg()).isEqualTo("이미 사용 중인 이메일입니다.");
    }

    @Test
    void checkEmailDuplicate_available() {
        String email = "new@example.com";
        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        ResponseWithStatus<Void> response = userService.checkEmailDuplicate(email);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("사용 가능한 이메일입니다.");
    }

    @Test
    void getUserProfile_userExists() {
        int userId = 1;

        User user = new User();
        user.setId(userId);
        user.setName("홍길동");
        user.setEmail("hong@example.com");
        user.setProfileImg("/profile.png");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        ResponseWithStatus<UserProfileResponseDto> response = userService.getUserProfile(userId);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData().getName()).isEqualTo("홍길동");
        assertThat(response.getBody().getMsg()).isEqualTo("프로필 조회 성공");
    }

    @Test
    void getUserProfile_userNotFound() {
        int userId = 1;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        ResponseWithStatus<UserProfileResponseDto> response = userService.getUserProfile(userId);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMsg()).isEqualTo("사용자를 찾을 수 없습니다.");
    }
    @Test
    void updateUserName_success() {
        int userId = 1;
        String newName = "김철수";

        User user = new User();
        user.setId(userId);
        user.setName("홍길동");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        ResponseWithStatus<Void> response = userService.updateUserName(userId, newName);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("이름 변경 성공");

        assertThat(user.getName()).isEqualTo("김철수");
        verify(userRepository).save(user);
    }

    @Test
    void updateUserName_userNotFound() {
        int userId = 1;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        ResponseWithStatus<Void> response = userService.updateUserName(userId, "김철수");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMsg()).isEqualTo("사용자를 찾을 수 없습니다.");

    }

    @Test
    void updateUserName_invalidName() {
        int userId = 1;

        User user = new User();
        user.setId(userId);
        user.setName("홍길동");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        ResponseWithStatus<Void> response = userService.updateUserName(userId, "   ");

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMsg()).isEqualTo("이름을 입력해주세요.");
        verify(userRepository, never()).save(any());
    }

    @Test
    void changePassword_success() {
        int userId = 1;
        UserInfo currentUser = UserInfo.builder()
                .id(userId)
                .email("test@example.com")
                .name("홍길동")
                .profileImg("/profile.png")
                .build();

        User user = new User();
        user.setId(userId);
        user.setPassword("encodedCurrentPassword");

        ChangePasswordRequestDto request = new ChangePasswordRequestDto();
        request.setCurrentPassword("currentPassword");
        request.setNewPassword("newPassword");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches("currentPassword", "encodedCurrentPassword")).willReturn(true);
        given(passwordEncoder.matches("newPassword", "encodedCurrentPassword")).willReturn(false);
        given(passwordEncoder.encode("newPassword")).willReturn("encodedNewPassword");

        ResponseWithStatus<Void> response = userService.changePassword(request, currentUser);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("비밀번호 변경 성공");
        assertThat(user.getPassword()).isEqualTo("encodedNewPassword");

        verify(userRepository).save(user);
    }

}

