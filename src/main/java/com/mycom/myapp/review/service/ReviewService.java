package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.LikeResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto writeReview(ReviewRequestDto requestDto, LoginResponseDto loginUser);
    LikeResponseDto likeReview(int reviewId, LoginResponseDto loginUser);
}
