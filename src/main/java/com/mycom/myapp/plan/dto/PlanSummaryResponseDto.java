package com.mycom.myapp.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanSummaryResponseDto {
  private Integer id;
  private String title;
  private String departureName;
  private String arrivalName;
  private String startDate;
  private String endDate;
  private String code;
}
