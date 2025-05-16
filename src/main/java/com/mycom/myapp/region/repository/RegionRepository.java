package com.mycom.myapp.region.repository;

import com.mycom.myapp.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
