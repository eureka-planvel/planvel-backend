package com.mycom.myapp.accommodation.repository;

import com.mycom.myapp.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
    List<Accommodation> findByRegionId(int regionId);
}
