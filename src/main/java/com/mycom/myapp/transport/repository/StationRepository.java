package com.mycom.myapp.transport.repository;

import com.mycom.myapp.transport.entity.Station;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {

  List<Station> findByRegionId(int regionId);

}