package com.mycom.myapp.review.dto;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private int regionId;
    private String title;
    private String content;
}
