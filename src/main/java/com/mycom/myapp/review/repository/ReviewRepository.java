package com.mycom.myapp.review.repository;

import com.mycom.myapp.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
