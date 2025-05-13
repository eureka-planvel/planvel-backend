package com.mycom.myapp.review.service;

import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.ReviewRepository;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public void writeReview(ReviewRequestDto requestDto, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Review review = Review.builder()
                .user(user)
                .region(requestDto.getRegion())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

}
