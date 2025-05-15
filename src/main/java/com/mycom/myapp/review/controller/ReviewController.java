package com.mycom.myapp.review.controller;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.*;
import com.mycom.myapp.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성", description = "새로운 리뷰를 작성합니다.")
    @PostMapping
    public ResponseEntity<ReviewResponseDto> writeReview(@RequestBody ReviewRequestDto requestDto,
                                                         Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal() instanceof String) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

        ReviewResponseDto responseDto = reviewService.writeReview(requestDto, loginUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "리뷰 수정", description = "리뷰 ID로 리뷰를 수정합니다.")
    @PutMapping("/{review_id}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable("review_id") int reviewId,
            @RequestBody ReviewUpdateRequestDto dto,
            Authentication authentication) {

        if(authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal() instanceof String) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

        try {
            ReviewResponseDto updatedReview = reviewService.updateReview(reviewId, loginUser, dto);
            return ResponseEntity.ok(updatedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "리뷰 좋아요", description = "특정 리뷰에 좋아요를 누릅니다.")
    @PostMapping("/like/{reviewId}")
    public ResponseEntity<LikeResponseDto> likeReview(@PathVariable int reviewId,
                                                      Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal() instanceof String) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

        LikeResponseDto response = reviewService.likeReview(reviewId, loginUser);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모든 리뷰 조회", description = "좋아요 순으로 정렬된 모든 리뷰를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        List<ReviewResponseDto> reviews = reviewService.getAllReviewsSortedByLikes();
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "리뷰 삭제", description = "특정 리뷰를 삭제합니다.")
    @DeleteMapping("/{review_id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("review_id") int reviewId,
                                             Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal() instanceof String) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponseDto loginUser = (LoginResponseDto) authentication.getPrincipal();

        try {
            reviewService.deleteReview(reviewId, loginUser);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "지역별 리뷰 조회", description = "특정 지역 ID의 리뷰를 좋아요 순으로 조회합니다.")
    @GetMapping("/{region_id}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByRegion(@PathVariable("region_id") int regionId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByRegionSortedByLikes(regionId);
        return ResponseEntity.ok(reviews);

    }

    @Operation(summary = "내가 작성한 리뷰 조회", description = "로그인한 사용자가 작성한 리뷰를 조회합니다.")
    @GetMapping("/my")
    public ResponseEntity<List<ReviewResponseDto>> getMyReviews(@AuthenticationPrincipal LoginResponseDto loginUser) {
        List<ReviewResponseDto> myReviews = reviewService.getMyReviews(loginUser);
        return ResponseEntity.ok(myReviews);

    }
}
