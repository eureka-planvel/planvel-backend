package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.review.dto.ReviewUpdateResponseDto;

public interface ReviewService {
    ReviewUpdateResponseDto updateReview(int reviewId, LoginResponseDto loginUser, ReviewUpdateRequestDto dto);
}
