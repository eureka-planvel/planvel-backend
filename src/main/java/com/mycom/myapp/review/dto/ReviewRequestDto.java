package com.mycom.myapp.review.dto;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private String region;
    private String title;
    private String content;
}
