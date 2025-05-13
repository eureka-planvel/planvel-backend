package com.mycom.myapp.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponseDto {
    private int id;
    private String userName;
    private String region;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private int likesCount;
}
