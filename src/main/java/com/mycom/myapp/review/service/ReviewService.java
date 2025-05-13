package com.mycom.myapp.review.service;

import com.mycom.myapp.review.dto.ReviewRequestDto;

public interface ReviewService {
    void writeReview(ReviewRequestDto requestDto, int userId);
}
