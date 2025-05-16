package com.mycom.myapp.review.dto;

import com.mycom.myapp.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponseDto {
    private int id;
    private String userName;
    private String userProfileImg;
    private String region;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;

    public static ReviewResponseDto from(Review review) {
        return ReviewResponseDto.builder()
            .id(review.getId())
            .userName(review.getUser().getName())
            .userProfileImg(review.getUser().getProfileImg())
            .region(review.getRegion().getName())
            .title(review.getTitle())
            .content(review.getContent())
            .createdAt(review.getCreatedAt())
            .updatedAt(review.getUpdatedAt())
            .likesCount(review.getLikesCount())
            .build();
    }
}
