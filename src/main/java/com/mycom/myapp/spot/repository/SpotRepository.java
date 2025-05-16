package com.mycom.myapp.spot.repository;

import com.mycom.myapp.accommodation.entity.Accommodation;
import com.mycom.myapp.spot.entity.Spot;
import com.mycom.myapp.spot.entity.type.SpotType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Integer> {

  @Query("SELECT s FROM Spot s JOIN FETCH s.region WHERE s.region.id = :regionId AND s.type = :type")
  Page<Spot> findByRegionIdAndType(@Param("regionId") int regionId, @Param("type") SpotType type, Pageable pageable);

}
