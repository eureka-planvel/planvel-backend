package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.review.dto.*;

import com.mycom.myapp.user.dto.UserInfo;
import java.util.List;

public interface ReviewService {
    
    ResponseWithStatus<Void> writeReview(ReviewRequestDto requestDto, UserInfo user);

    ResponseWithStatus<Void> updateReview(int reviewId, UserInfo userInfo, ReviewUpdateRequestDto dto);

    ResponseWithStatus<LikeResponseDto> addLike(int reviewId, UserInfo userInfo);

    ResponseWithStatus<LikeResponseDto> removeLike(int reviewId, UserInfo userInfo);
    
    ResponseWithStatus<Void> deleteReview(int reviewId, UserInfo userInfo);

    ResponseWithStatus<List<ReviewResponseDto>> getReviewsByRegionSortedByCreatedAt(int regionId);

    ResponseWithStatus<List<ReviewResponseDto>> getReviewsByRegionSortedByLikes(int regionId);

    ResponseWithStatus<List<ReviewResponseDto>> getTop5ReviewsSortedByLikes();

    ResponseWithStatus<List<ReviewResponseDto>> getMyReviews(int id);
}