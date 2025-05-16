package com.mycom.myapp.transport.dto;

import com.mycom.myapp.transport.entity.Timetable;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TimetableResponseDto {
  private int id;
  private String departureStationName;
  private String arrivalStationName;
  private String departureRegionName;
  private String arrivalRegionName;
  private LocalTime departureTime;
  private int durationMin;
  private int price;
  private String transportNumber;

  public static TimetableResponseDto from(Timetable timetable) {
    return TimetableResponseDto.builder()
        .id(timetable.getId())
        .departureStationName(timetable.getDepartureStation().getName())
        .arrivalStationName(timetable.getArrivalStation().getName())
        .departureRegionName(timetable.getDepartureStation().getRegion().getName())
        .arrivalRegionName(timetable.getArrivalStation().getRegion().getName())
        .departureTime(timetable.getDepartureTime())
        .durationMin(timetable.getDurationMin())
        .price(timetable.getPrice())
        .transportNumber(timetable.getTransportNumber())
        .build();
  }
}