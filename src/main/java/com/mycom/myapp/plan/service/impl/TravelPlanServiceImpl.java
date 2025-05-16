package com.mycom.myapp.plan.service.impl;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.plan.dto.PlanDetailResponseDto;
import com.mycom.myapp.plan.dto.PlanSaveRequestDto;
import com.mycom.myapp.plan.dto.PlanSummaryResponseDto;
import com.mycom.myapp.plan.entity.TravelPlan;
import com.mycom.myapp.plan.entity.TravelSchedule;
import com.mycom.myapp.plan.entity.type.AccommodationType;
import com.mycom.myapp.plan.reponsitory.TravelPlanRepository;
import com.mycom.myapp.plan.reponsitory.TravelScheduleRepository;
import com.mycom.myapp.plan.service.TravelPlanService;
import com.mycom.myapp.spot.entity.type.SpotType;
import com.mycom.myapp.user.entity.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TravelPlanServiceImpl implements TravelPlanService {

  private final TravelPlanRepository travelPlanRepository;
  private final TravelScheduleRepository travelScheduleRepository;

  @Transactional
  @Override
  public ResponseWithStatus<Void> saveTravelPlan(int userId, PlanSaveRequestDto requestDto) {
    // 1. TravelPlan 생성 및 저장
    PlanSaveRequestDto.TravelInfo info = requestDto.getTravelInfo();
    if (info == null || info.getDepartureId() == null || info.getArrivalId() == null) {
      return ResponseWithStatus.badRequest(CommonResponse.fail("필수 여행 정보 없음"));
    }

    try {

      TravelPlan plan = new TravelPlan();
      plan.setCode(generateRandomCode());

      User user = new User();
      user.setId(userId);
      plan.setUser(user);

      plan.setDepartureId(info.getDepartureId());
      plan.setDepartureName(info.getDepartureName());
      plan.setArrivalId(info.getArrivalId());
      plan.setArrivalName(info.getArrivalName());
      plan.setDepartureStationId(info.getDepartureStation().getId());
      plan.setDepartureStationName(info.getDepartureStation().getName());
      plan.setArrivalStationId(info.getArrivalStation().getId());
      plan.setArrivalStationName(info.getArrivalStation().getName());
      plan.setDepartureScheduleId(info.getDepartureSchedule().getId());
      plan.setReturnScheduleId(info.getReturnSchedule().getId());
      plan.setTransport(info.getTransport());
      plan.setAccommodationId(info.getAccommodation().getId());
      plan.setAccommodationName(info.getAccommodation().getName());
      plan.setAccommodationType(
          info.getAccommodation().getIsHotel() ? AccommodationType.호텔 : AccommodationType.기타);
      plan.setStartDate(info.getStartDate());
      plan.setEndDate(info.getEndDate());

      travelPlanRepository.save(plan);

      // 2. TravelSchedule 저장
      requestDto.getScheduleData().forEach((date, spots) -> {
        spots.forEach(spot -> {
          TravelSchedule schedule = new TravelSchedule();
          schedule.setTravelPlan(plan);
          schedule.setScheduleDate(LocalDate.parse(date));
          schedule.setSpotId(spot.getId());
          schedule.setSpotName(spot.getSpotName());
          schedule.setSpotType(SpotType.valueOf(spot.getType()));
          schedule.setRegionName(spot.getRegionName());
          schedule.setAddress(spot.getAddress());
          schedule.setImageUrl(spot.getImageUrl());
          schedule.setThumbnailUrl(spot.getThumbnailUrl());
          travelScheduleRepository.save(schedule);
        });
      });

      return ResponseWithStatus.ok(CommonResponse.success("여행 플랜 저장 성공"));


    } catch (Exception e) {
      return ResponseWithStatus.conflict(CommonResponse.fail("저장 중 오류"));
    }

  }

  @Override
  public ResponseWithStatus<List<PlanSummaryResponseDto>> getMyTravelPlans(int userId) {
    List<TravelPlan> plans = travelPlanRepository.findByUserId(userId);

    List<PlanSummaryResponseDto> dtoList = plans.stream()
        .map(plan -> PlanSummaryResponseDto.builder()
            .id(plan.getId())
            .title(buildPlanTitle(plan))
            .departureName(plan.getDepartureName())
            .arrivalName(plan.getArrivalName())
            .startDate(plan.getStartDate().toString())
            .endDate(plan.getEndDate().toString())
            .code(plan.getCode())
            .build())
        .collect(Collectors.toList());

    return ResponseWithStatus.ok(CommonResponse.success(dtoList, "조회 성공"));
  }

  @Override
  public ResponseWithStatus<PlanDetailResponseDto> getTravelPlanDetailByCode(String code) {
    TravelPlan plan = travelPlanRepository.findByCode(code)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 여행 계획이 없습니다."));

    // 일정 날짜 기준 Grouping -> Map<LocalDate, List<TravelSchedule>>
    Map<LocalDate, List<TravelSchedule>> groupedSchedules = plan.getSchedules().stream()
        .collect(Collectors.groupingBy(TravelSchedule::getScheduleDate));

    // 날짜별로 day 번호 붙이기 (1일차부터)
    List<PlanDetailResponseDto.ScheduleDto> scheduleDtoList = new ArrayList<>();
    List<LocalDate> sortedDates = groupedSchedules.keySet().stream()
        .sorted()
        .toList();

    for (int i = 0; i < sortedDates.size(); i++) {
      LocalDate date = sortedDates.get(i);
      List<String> spotNames = groupedSchedules.get(date).stream()
          .map(TravelSchedule::getSpotName)
          .collect(Collectors.toList());

      PlanDetailResponseDto.ScheduleDto dto = PlanDetailResponseDto.ScheduleDto.builder()
          .day(i + 1)  // 1일차부터
          .spots(spotNames)
          .build();

      scheduleDtoList.add(dto);
    }

    PlanDetailResponseDto detailDto = PlanDetailResponseDto.builder()
        .title(buildPlanTitle(plan))
        .departureName(plan.getDepartureName())
        .arrivalName(plan.getArrivalName())
        .startDate(plan.getStartDate().toString())
        .endDate(plan.getEndDate().toString())
        .transport(plan.getTransport())
        .accommodation(plan.getAccommodationName())
        .schedule(scheduleDtoList)
        .build();

    return ResponseWithStatus.ok(CommonResponse.success(detailDto, "상세 조회 성공"));
  }

  private String generateRandomCode() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
  }

  private String buildPlanTitle(TravelPlan plan) {
    return String.format("%s → %s (%s ~ %s)",
        plan.getDepartureName(),
        plan.getArrivalName(),
        plan.getStartDate(),
        plan.getEndDate());
  }
}
