package com.mycom.myapp.transport.dto;

import com.mycom.myapp.transport.entity.Station;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StationResponseDto {
  private int id;
  private String name;
  private String type;

  public static StationResponseDto from(Station station) {
    return StationResponseDto.builder()
        .id(station.getId())
        .name(station.getName())
        .type(station.getType().name())
        .build();
  }
}