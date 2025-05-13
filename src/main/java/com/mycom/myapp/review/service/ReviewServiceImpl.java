package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.LikeResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;
import com.mycom.myapp.review.entity.Like;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.LikeRepository;
import com.mycom.myapp.review.repository.ReviewRepository;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public ReviewResponseDto writeReview(ReviewRequestDto requestDto, LoginResponseDto loginUser) {
        User user = userRepository.findById(loginUser.getId())
                .orElseThrow( () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Review review = Review.builder()
                .user(user)
                .region(requestDto.getRegion())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .id(saved.getId())
                .userName(user.getName())
                .region(saved.getRegion())
                .title(saved.getTitle())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .likesCount(saved.getLikesCount())
                .build();
    }

    @Override
    public LikeResponseDto likeReview(int reviewId, LoginResponseDto loginUser) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        User user = userRepository.findById(loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Optional<Like> alreadyLiked = likeRepository.findByReviewIdAndUserId(reviewId, user.getId());

        boolean liked;
        if (alreadyLiked.isPresent()) {
            likeRepository.delete(alreadyLiked.get());
            review.setLikesCount(review.getLikesCount() - 1);
            liked = false;
        } else {
            Like like = Like.builder()
                    .review(review)
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .build();

            likeRepository.save(like);
            review.setLikesCount(review.getLikesCount() + 1);
            liked = true;
        }

        reviewRepository.save(review);

        return LikeResponseDto.builder()
                .liked(liked)
                .likesCount(review.getLikesCount())
                .build();
    }

}
