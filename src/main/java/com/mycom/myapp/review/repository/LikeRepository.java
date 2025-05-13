package com.mycom.myapp.review.repository;

import com.mycom.myapp.review.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    Optional<Like> findByReviewIdAndUserId(int reviewId, int userId);
}
