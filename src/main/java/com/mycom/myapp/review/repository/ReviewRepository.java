package com.mycom.myapp.review.repository;

import com.mycom.myapp.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r JOIN FETCH r.user JOIN FETCH r.region ORDER BY r.likesCount DESC")
    List<Review> findAllWithUserSortedByLikes();


    @Query("SELECT r FROM Review r JOIN FETCH r.user JOIN FETCH r.region WHERE r.region.id = :regionId ORDER BY r.likesCount DESC")
    List<Review> findAllByRegionIdSortedByLikes(@Param("regionId") int regionId);


    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    List<Review> findAllByUserIdWithRegion(@Param("userId") int userId);

}
