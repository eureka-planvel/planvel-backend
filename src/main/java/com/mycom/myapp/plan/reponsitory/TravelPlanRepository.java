package com.mycom.myapp.plan.reponsitory;

import com.mycom.myapp.plan.entity.TravelPlan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {

  Optional<TravelPlan> findByCode(String code);

  List<TravelPlan> findByUserId(int userId);
}