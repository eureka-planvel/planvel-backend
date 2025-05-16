package com.mycom.myapp.review.service.impl;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.region.repository.RegionRepository;
import com.mycom.myapp.review.dto.LikeResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewResponseDto;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.review.entity.Like;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.LikeRepository;
import com.mycom.myapp.review.repository.ReviewRepository;
import com.mycom.myapp.review.service.ReviewService;
import com.mycom.myapp.user.dto.UserInfo;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final RegionRepository regionRepository;


    @Override
    public ResponseWithStatus<Void> writeReview(ReviewRequestDto requestDto,
        UserInfo userInfo) {

        User user = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Region region = regionRepository.findById(requestDto.getRegionId())
                .orElseThrow( () -> new IllegalArgumentException("해당 지역을 찾을 수 없습니다."));

        Review review = Review.builder()
                .user(user)
                .region(region)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .likesCount(0)
                .build();

        reviewRepository.save(review);

        return ResponseWithStatus.ok(CommonResponse.success("리뷰 등록 성공"));
    }

    @Override
    @Transactional
    public ResponseWithStatus<Void> updateReview(int reviewId, UserInfo userInfo, ReviewUpdateRequestDto request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.isWriter(userInfo)) {
            return ResponseWithStatus.forbidden(CommonResponse.fail("본인만 리뷰를 수정할 수 있습니다."));
        }

        try {
            review.updateTitleAndContent(request);
        } catch (IllegalArgumentException e) {
            return ResponseWithStatus.badRequest(CommonResponse.fail(e.getMessage()));
        }

        return ResponseWithStatus.ok(CommonResponse.success("리뷰 수정 성공"));
    }

    @Override
    public ResponseWithStatus<LikeResponseDto> addLike(int reviewId, UserInfo userInfo) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        User user = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Like like = Like.builder()
            .review(review)
            .user(user)
            .createdAt(LocalDateTime.now())
            .build();

        likeRepository.save(like);
        review.incrementLikes();
        reviewRepository.save(review);

        LikeResponseDto responseDto = LikeResponseDto.of(true, review.getLikesCount());
        return ResponseWithStatus.ok(CommonResponse.success(responseDto, "좋아요 성공"));
    }

    @Override
    public ResponseWithStatus<LikeResponseDto> removeLike(int reviewId, UserInfo userInfo) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        User user = userRepository.findById(userInfo.getId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Like like = likeRepository.findByReviewAndUser(review, user)
            .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않았습니다."));

        likeRepository.delete(like);
        review.decrementLikes();
        reviewRepository.save(review);

        LikeResponseDto responseDto = LikeResponseDto.of(false, review.getLikesCount());
        return ResponseWithStatus.ok(CommonResponse.success(responseDto, "좋아요 취소 성공"));
    }

    @Override
    public ResponseWithStatus<List<ReviewResponseDto>> getReviewsByRegionSortedByCreatedAt(int regionId) {
        List<Review> reviews = reviewRepository.findAllByRegionIdSortedByCreatedAt(regionId);

        List<ReviewResponseDto> dtos = reviews.stream()
            .map(ReviewResponseDto::from)
            .collect(Collectors.toList());

        return ResponseWithStatus.ok(CommonResponse.success(dtos, "지역별 최신 리뷰 조회 성공"));
    }

    @Override
    public ResponseWithStatus<List<ReviewResponseDto>> getReviewsByRegionSortedByLikes(int regionId) {
        List<Review> reviews = reviewRepository.findAllByRegionIdSortedByLikes(regionId);

        List<ReviewResponseDto> dtos = reviews.stream()
            .map(ReviewResponseDto::from)
            .collect(Collectors.toList());

        return ResponseWithStatus.ok(CommonResponse.success(dtos, "지역별 인기순 리뷰 조회 성공"));
    }

    @Override
    public ResponseWithStatus<List<ReviewResponseDto>> getTop5ReviewsSortedByLikes() {
        List<Review> reviews = reviewRepository.findTop5ByOrderByLikesCountDesc();

        List<ReviewResponseDto> dtos = reviews.stream()
            .map(ReviewResponseDto::from)
            .collect(Collectors.toList());

        return ResponseWithStatus.ok(CommonResponse.success(dtos, "인기 리뷰 TOP 5 조회 성공"));
    }

    @Override
    @Transactional
    public ResponseWithStatus<Void> deleteReview(int reviewId, UserInfo userInfo) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        if (!review.isWriter(userInfo)) {
            return ResponseWithStatus.forbidden(CommonResponse.fail("본인만 리뷰를 삭제할 수 있습니다."));
        }

        reviewRepository.delete(review);
        return ResponseWithStatus.ok(CommonResponse.success("리뷰 삭제 성공"));
    }


    @Override
    public ResponseWithStatus<List<ReviewResponseDto>> getMyReviews(int id) {
        List<Review> reviews = reviewRepository.findAllByUserIdWithRegion(id);

        List<ReviewResponseDto> dtos = reviews.stream()
            .map(ReviewResponseDto::from)
            .collect(Collectors.toList());

        return ResponseWithStatus.ok(CommonResponse.success(dtos, "내 리뷰 목록 조회 성공"));
    }

}
