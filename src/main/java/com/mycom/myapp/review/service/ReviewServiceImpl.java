package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.review.dto.ReviewUpdateResponseDto;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.ReviewRepository;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Override
    @Transactional
    public ReviewUpdateResponseDto updateReview(int reviewId, LoginResponseDto loginUser, ReviewUpdateRequestDto dto){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getUser().getId() != loginUser.getId()) {
            throw new SecurityException("본인만 리뷰를 수정할 수 있습니다.");
        }

        boolean isTitleModified = dto.getTitle() != null;
        boolean isContentModified = dto.getContent() != null;

        if (!isTitleModified && !isContentModified) {
            return ReviewUpdateResponseDto.builder()
                    .id(review.getId())
                    .userName(review.getUser().getName())
                    .region(review.getRegion())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .build();
        }

        if (isTitleModified) {
            if (dto.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("제목을 빈 칸으로 수정할 수 없습니다.");
            }
            review.setTitle(dto.getTitle().trim());
        }

        if (isContentModified) {
            if (dto.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("내용을 빈 칸으로 수정할 수 없습니다.");
            }
            review.setContent(dto.getContent().trim());
        }

        reviewRepository.flush();

        return ReviewUpdateResponseDto.builder()
                .id(review.getId())
                .userName(review.getUser().getName())
                .region(review.getRegion())
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

}
