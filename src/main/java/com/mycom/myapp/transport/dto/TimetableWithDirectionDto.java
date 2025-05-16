package com.mycom.myapp.transport.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TimetableWithDirectionDto {
  private int id;
  private String transportType;
  private String departureTime;
  private int durationMin;
  private int price;
  private String transportNumber;
  private String departureStationName;
  private String arrivalStationName;
  private String departureRegionName;
  private String arrivalRegionName;
  private String direction; // "DEPARTURE" or "RETURN"
}
