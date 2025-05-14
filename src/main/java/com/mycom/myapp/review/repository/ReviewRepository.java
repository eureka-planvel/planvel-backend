package com.mycom.myapp.review.repository;

import com.mycom.myapp.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r JOIN FETCH r.user JOIN FETCH r.region ORDER BY r.likesCount DESC")
    List<Review> findAllWithUserSortedByLikes();
}
