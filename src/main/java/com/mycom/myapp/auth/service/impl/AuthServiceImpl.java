package com.mycom.myapp.auth.service.impl;

import com.mycom.myapp.auth.dto.request.LoginRequestDto;
import com.mycom.myapp.auth.service.AuthService;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.user.dto.UserInfo;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseWithStatus<Void> login(LoginRequestDto loginRequestDto, HttpSession session) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequestDto.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseWithStatus.unauthorized(CommonResponse.fail("이메일 또는 비밀번호가 일치하지 않습니다."));
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return ResponseWithStatus.unauthorized(CommonResponse.fail("이메일 또는 비밀번호가 일치하지 않습니다."));
        }

        UserInfo userInfo = UserInfo.fromEntity(user);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userInfo, null, Collections.emptyList());

        authentication.setDetails(new WebAuthenticationDetails(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return ResponseWithStatus.ok(CommonResponse.success("로그인 성공"));
    }
}
