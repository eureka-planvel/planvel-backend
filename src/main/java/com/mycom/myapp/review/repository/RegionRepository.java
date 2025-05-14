package com.mycom.myapp.review.repository;

import com.mycom.myapp.review.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
