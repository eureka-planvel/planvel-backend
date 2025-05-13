package com.mycom.myapp.review.controller;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.LikeResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;
import com.mycom.myapp.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

}
