package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.review.dto.ReviewUpdateResponseDto;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    @Override
    @Transactional
    public ReviewUpdateResponseDto updateReview(int reviewId, LoginResponseDto loginUser, ReviewUpdateRequestDto dto){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getUser().getId() != loginUser.getId()) {
            throw new SecurityException("본인만 리뷰를 수정할 수 있습니다.");
        }

        if (!StringUtils.hasText(dto.getTitle()) && !StringUtils.hasText(dto.getContent())) {
            throw new IllegalArgumentException("수정할 제목 또는 내용을 입력하세요.");
        }

        if (StringUtils.hasText(dto.getTitle())) {
            review.setTitle(dto.getTitle().trim());
        }

        if (StringUtils.hasText(dto.getContent())) {
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
