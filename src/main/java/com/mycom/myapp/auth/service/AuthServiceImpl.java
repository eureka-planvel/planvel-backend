package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow( () -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.") );

        if( !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()) ) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return LoginResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImg(user.getProfileImg())
                .build();
    }
}
