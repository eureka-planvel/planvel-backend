package com.mycom.myapp.review.controller;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.*;
import com.mycom.myapp.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

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

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        List<ReviewResponseDto> reviews = reviewService.getAllReviewsSortedByLikes();
        return ResponseEntity.ok(reviews);
    }

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

}
