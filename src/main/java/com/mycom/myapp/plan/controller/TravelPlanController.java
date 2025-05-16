package com.mycom.myapp.plan.controller;

import com.mycom.myapp.common.resolver.LoginUser;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.plan.dto.PlanDetailResponseDto;
import com.mycom.myapp.plan.dto.PlanSaveRequestDto;
import com.mycom.myapp.plan.dto.PlanSummaryResponseDto;
import com.mycom.myapp.plan.service.TravelPlanService;
import com.mycom.myapp.user.dto.UserInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class TravelPlanController {

  private final TravelPlanService travelPlanService;

  @PostMapping("/save")
  public ResponseEntity<CommonResponse<Void>> saveTravelPlan(
      @LoginUser UserInfo userInfo,
      @RequestBody PlanSaveRequestDto requestDto
  ) {
    ResponseWithStatus<Void> response = travelPlanService.saveTravelPlan(userInfo.getId(), requestDto);
    return ResponseEntity.status(response.getStatus()).body(response.getBody());
  }

  @GetMapping("/my-travels")
  public ResponseEntity<CommonResponse<List<PlanSummaryResponseDto>>> getMyTravelPlans(
      @LoginUser UserInfo userInfo
  ) {
    ResponseWithStatus<List<PlanSummaryResponseDto>> response = travelPlanService.getMyTravelPlans(userInfo.getId());
    return ResponseEntity.status(response.getStatus()).body(response.getBody());
  }

  @GetMapping("/{code}")
  public ResponseEntity<CommonResponse<PlanDetailResponseDto>> getTravelPlanDetail(
      @PathVariable String code
  ) {
    ResponseWithStatus<PlanDetailResponseDto> response = travelPlanService.getTravelPlanDetailByCode(code);
    return ResponseEntity.status(response.getStatus()).body(response.getBody());
  }


}