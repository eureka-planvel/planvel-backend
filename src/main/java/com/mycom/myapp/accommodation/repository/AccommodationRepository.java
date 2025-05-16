package com.mycom.myapp.accommodation.repository;

import com.mycom.myapp.accommodation.entity.Accommodation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {

  @Query("SELECT a FROM Accommodation a JOIN a.region r WHERE r.id = :regionId")
  Page<Accommodation> findAllByRegionIdWithRegion(@Param("regionId") int regionId, Pageable pageable);


  @Query("SELECT a FROM Accommodation a JOIN FETCH a.region WHERE a.id = :id")
  Optional<Accommodation> findByIdWithRegion(@Param("id") int id);
}
