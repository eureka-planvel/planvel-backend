package com.mycom.myapp.plan.reponsitory;

import com.mycom.myapp.plan.entity.TravelSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelScheduleRepository extends JpaRepository<TravelSchedule, Long> {

  List<TravelSchedule> findAllByTravelPlanId(Long travelPlanId);
}