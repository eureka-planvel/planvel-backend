package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.review.dto.ReviewUpdateResponseDto;

public interface ReviewService {
    ReviewResponseDto writeReview(ReviewRequestDto requestDto, LoginResponseDto loginUser);
    ReviewUpdateResponseDto updateReview(int reviewId, LoginResponseDto loginUser, ReviewUpdateRequestDto dto);
}