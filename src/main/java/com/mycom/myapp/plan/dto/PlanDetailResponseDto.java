package com.mycom.myapp.plan.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDetailResponseDto {
  private String title;
  private String departureName;
  private String arrivalName;
  private String startDate;
  private String endDate;
  private String transport;
  private String accommodation;
  private List<ScheduleDto> schedule;

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ScheduleDto {
    private int day;
    private List<SpotDto> spots;
  }

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SpotDto {
    private String spotName;
    private String imageUrl;
    private String regionName;
    private String address;
  }
}