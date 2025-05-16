package com.mycom.myapp.review.controller;

import com.mycom.myapp.common.resolver.LoginUser;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.review.dto.LikeResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.review.service.ReviewService;
import com.mycom.myapp.user.dto.UserInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> writeReview(
        @RequestBody ReviewRequestDto requestDto, @LoginUser UserInfo userInfo){
        ResponseWithStatus<Void> response = reviewService.writeReview(requestDto, userInfo);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }

    @PutMapping("/{review_id}")
    public ResponseEntity<CommonResponse<Void>> updateReview(
            @PathVariable("review_id") int reviewId,
            @RequestBody ReviewUpdateRequestDto dto,
            @LoginUser UserInfo userInfo) {

        ResponseWithStatus<Void> response = reviewService.updateReview(reviewId, userInfo, dto);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());

    }

    @PostMapping("/{reviewId}/like")
    public ResponseEntity<CommonResponse<LikeResponseDto>> likeReview(
        @PathVariable int reviewId,
        @LoginUser UserInfo userInfo) {

        ResponseWithStatus<LikeResponseDto> response = reviewService.addLike(reviewId, userInfo);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }

    @DeleteMapping("/{reviewId}/like")
    public ResponseEntity<CommonResponse<LikeResponseDto>> unlikeReview(
        @PathVariable int reviewId,
        @LoginUser UserInfo userInfo) {

        ResponseWithStatus<LikeResponseDto> response = reviewService.removeLike(reviewId, userInfo);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getReviewsByRegion(@PathVariable int regionId) {
        ResponseWithStatus<List<ReviewResponseDto>> response = reviewService.getReviewsByRegionSortedByCreatedAt(regionId);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }


    @GetMapping("/region/{regionId}/popular")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getPopularReviewsByRegion(@PathVariable int regionId) {
        ResponseWithStatus<List<ReviewResponseDto>> response = reviewService.getReviewsByRegionSortedByLikes(regionId);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }


    @GetMapping("/popular") // 5개만
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getPopularReviews() {
        ResponseWithStatus<List<ReviewResponseDto>> response = reviewService.getTop5ReviewsSortedByLikes();
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }



    @DeleteMapping("/{reviewId}")
    public ResponseEntity<CommonResponse<Void>> deleteReview(
        @PathVariable int reviewId,
        @LoginUser UserInfo userInfo) {

        ResponseWithStatus<Void> response = reviewService.deleteReview(reviewId, userInfo);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }



    @GetMapping("/my")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getMyReviews(@LoginUser UserInfo userInfo) {
        ResponseWithStatus<List<ReviewResponseDto>> response = reviewService.getMyReviews(userInfo.getId());
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }
}
