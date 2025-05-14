package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.*;

public interface ReviewService {
    ReviewResponseDto writeReview(ReviewRequestDto requestDto, LoginResponseDto loginUser);
    ReviewResponseDto updateReview(int reviewId, LoginResponseDto loginUser, ReviewUpdateRequestDto dto);
    LikeResponseDto likeReview(int reviewId, LoginResponseDto loginUser);

}