package com.mycom.myapp.plan.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.plan.dto.PlanDetailResponseDto;
import com.mycom.myapp.plan.dto.PlanSaveRequestDto;
import com.mycom.myapp.plan.dto.PlanSummaryResponseDto;
import java.util.List;

public interface TravelPlanService {

  ResponseWithStatus<Void> saveTravelPlan(int id, PlanSaveRequestDto requestDto);

  ResponseWithStatus<List<PlanSummaryResponseDto>> getMyTravelPlans(int id);

  ResponseWithStatus<PlanDetailResponseDto> getTravelPlanDetailByCode(String code);
}
