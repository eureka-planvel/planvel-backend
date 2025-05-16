package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.service.impl.AuthServiceImpl;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpSession session;

    @Test
    void login_success() {
        String email = "test@example.com";
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword(rawPassword);

        User user = new User();
        user.setId(1);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setName("홍길동");
        user.setProfileImg("/profile.png");

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(rawPassword, encodedPassword)).willReturn(true);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));

        ResponseWithStatus<Void> response = authService.login(requestDto, session);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("로그인 성공");

    }

    @Test
    void login_fail_userNotFound() {
        String email = "notfound@example.com";

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword("password");

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        ResponseWithStatus<Void> response = authService.login(requestDto, session);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().getMsg()).isEqualTo("이메일 또는 비밀번호가 일치하지 않습니다.");

        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    void login_fail_passwordMismatch() {
        String email = "test@example.com";

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword("wrongPassword");

        User user = new User();
        user.setId(1);
        user.setEmail(email);
        user.setPassword("encodedPassword");

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongPassword", "encodedPassword")).willReturn(false);

        ResponseWithStatus<Void> response = authService.login(requestDto, session);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().getMsg()).isEqualTo("이메일 또는 비밀번호가 일치하지 않습니다.");

        verify(session, never()).setAttribute(anyString(), any());
    }
}

