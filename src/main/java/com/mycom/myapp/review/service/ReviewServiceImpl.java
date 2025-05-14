package com.mycom.myapp.review.service;

import com.mycom.myapp.auth.dto.response.LoginResponseDto;
import com.mycom.myapp.review.dto.*;
import com.mycom.myapp.review.entity.Like;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.LikeRepository;
import com.mycom.myapp.review.repository.ReviewRepository;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public ReviewResponseDto writeReview(ReviewRequestDto requestDto, LoginResponseDto loginUser) {
        User user = userRepository.findById(loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Review review = Review.builder()
                .user(user)
                .region(requestDto.getRegion())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .id(saved.getId())
                .userName(user.getName())
                .region(saved.getRegion())
                .title(saved.getTitle())
                .content(saved.getContent())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .likesCount(saved.getLikesCount())
                .build();
    }

    @Override
    @Transactional
    public ReviewResponseDto updateReview(int reviewId, LoginResponseDto loginUser, ReviewUpdateRequestDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (review.getUser().getId() != loginUser.getId()) {
            throw new SecurityException("본인만 리뷰를 수정할 수 있습니다.");
        }

        boolean isTitleModified = dto.getTitle() != null;
        boolean isContentModified = dto.getContent() != null;

        if (!isTitleModified && !isContentModified) {
            return ReviewResponseDto.builder()
                    .id(review.getId())
                    .userName(review.getUser().getName())
                    .region(review.getRegion())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .likesCount(review.getLikesCount())
                    .build();
        }

        if (isTitleModified) {
            if (dto.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("제목을 빈 칸으로 수정할 수 없습니다.");
            }
            review.setTitle(dto.getTitle().trim());
        }

        if (isContentModified) {
            if (dto.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("내용을 빈 칸으로 수정할 수 없습니다.");
            }
            review.setContent(dto.getContent().trim());
        }

        review.setUpdatedAt(LocalDateTime.now());

        return ReviewResponseDto.builder()
                .id(review.getId())
                .userName(review.getUser().getName())
                .region(review.getRegion())
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .likesCount(review.getLikesCount())
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

    @Override
    public List<ReviewResponseDto> getAllReviewsSortedByLikes() {
        List<Review> reviews = reviewRepository.findAllWithUserSortedByLikes();

        return reviews.stream()
                .map(review -> ReviewResponseDto.builder()
                        .id(review.getId())
                        .userName(review.getUser().getName())
                        .region(review.getRegion())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .likesCount(review.getLikesCount())
                        .build())
                .collect(Collectors.toList());
    }

}
