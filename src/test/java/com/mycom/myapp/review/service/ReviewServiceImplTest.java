package com.mycom.myapp.review.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.region.repository.RegionRepository;
import com.mycom.myapp.review.dto.LikeResponseDto;
import com.mycom.myapp.review.dto.ReviewRequestDto;
import com.mycom.myapp.review.dto.ReviewUpdateRequestDto;
import com.mycom.myapp.review.entity.Like;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.review.repository.LikeRepository;
import com.mycom.myapp.review.repository.ReviewRepository;
import com.mycom.myapp.review.service.impl.ReviewServiceImpl;
import com.mycom.myapp.user.dto.UserInfo;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private RegionRepository regionRepository;

    @Test
    void writeReview_success() {
        int userId = 1;
        int regionId = 10;
        UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .email("test@example.com")
                .name("홍길동")
                .profileImg("/profile.png")
                .build();

        User user = new User();
        user.setId(userId);

        Region region = new Region();
        region.setId(regionId);

        ReviewRequestDto requestDto = new ReviewRequestDto();
        requestDto.setRegionId(regionId);
        requestDto.setTitle("제목");
        requestDto.setContent("내용");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(regionRepository.findById(regionId)).willReturn(Optional.of(region));

        ResponseWithStatus<Void> response = reviewService.writeReview(requestDto, userInfo);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("리뷰 등록 성공");

        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void updateReview_success() {
        int reviewId = 1;
        int userId = 1;
        UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .email("test@example.com")
                .name("홍길동")
                .profileImg("/profile.png")
                .build();

        Review review = mock(Review.class);
        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));
        given(review.isWriter(userInfo)).willReturn(true);

        ReviewUpdateRequestDto request = new ReviewUpdateRequestDto();
        request.setTitle("수정된 제목");
        request.setContent("수정된 내용");

        ResponseWithStatus<Void> response = reviewService.updateReview(reviewId, userInfo, request);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("리뷰 수정 성공");

        verify(review).updateTitleAndContent(request);
    }

    @Test
    void addLike_success() {
        int reviewId = 1;
        int userId = 1;
        UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .email("test@example.com")
                .name("홍길동")
                .profileImg("/profile.png")
                .build();

        Review review = new Review();
        review.setId(reviewId);
        review.setLikesCount(0);

        User user = new User();
        user.setId(userId);

        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        ResponseWithStatus<LikeResponseDto> response = reviewService.addLike(reviewId, userInfo);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("좋아요 성공");
        assertThat(response.getBody().getData().isLiked()).isTrue();
        assertThat(response.getBody().getData().getLikesCount()).isEqualTo(1);

        verify(likeRepository).save(any(Like.class));
        verify(reviewRepository).save(review);
    }

    @Test
    void removeLike_success() {
        int reviewId = 1;
        int userId = 1;
        UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .email("test@example.com")
                .name("홍길동")
                .profileImg("/profile.png")
                .build();

        Review review = new Review();
        review.setId(reviewId);
        review.setLikesCount(1);

        User user = new User();
        user.setId(userId);

        Like like = new Like();

        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(likeRepository.findByReviewAndUser(review, user)).willReturn(Optional.of(like));

        ResponseWithStatus<LikeResponseDto> response = reviewService.removeLike(reviewId, userInfo);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("좋아요 취소 성공");
        assertThat(response.getBody().getData().isLiked()).isFalse();
        assertThat(response.getBody().getData().getLikesCount()).isEqualTo(0);

        verify(likeRepository).delete(like);
        verify(reviewRepository).save(review);
    }

    @Test
    void deleteReview_success() {
        int reviewId = 1;
        int userId = 1;
        UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .email("test@example.com")
                .name("홍길동")
                .profileImg("/profile.png")
                .build();

        Review review = mock(Review.class);
        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));
        given(review.isWriter(userInfo)).willReturn(true);

        ResponseWithStatus<Void> response = reviewService.deleteReview(reviewId, userInfo);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMsg()).isEqualTo("리뷰 삭제 성공");

        verify(reviewRepository).delete(review);
    }

}

