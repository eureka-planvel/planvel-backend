package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.ReviewRepository;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewResponseDto writeReview(ReviewRequestDto requestDto, LoginResponseDto loginUser) {
        User user = userRepository.findById(loginUser.getId())
                .orElseThrow( () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Review review = Review.builder()
                .user(user)
                .region(requestDto.getRegion())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .id(saved.getId())
                .userName(user.getName())
                .region(saved.getRegion())
                .title(saved.getTitle())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .build();
    }

}
