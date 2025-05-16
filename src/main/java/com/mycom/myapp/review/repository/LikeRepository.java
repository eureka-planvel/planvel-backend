package com.mycom.myapp.review.repository;

import com.mycom.myapp.review.entity.Like;
import com.mycom.myapp.review.entity.Review;
import com.mycom.myapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    void deleteByReviewId(int reviewId);

    Optional<Like> findByReviewAndUser(Review review, User user);
}
