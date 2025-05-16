package com.mycom.myapp.transport.controller;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.transport.dto.StationResponseDto;
import com.mycom.myapp.transport.dto.TimetableResponseDto;
import com.mycom.myapp.transport.dto.TimetableWithDirectionDto;
import com.mycom.myapp.transport.entity.type.TransportType;
import com.mycom.myapp.transport.service.StationService;
import com.mycom.myapp.transport.service.TimetableService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class TimetableController {

  private final TimetableService timetableService;
  private final StationService stationService;

  // 1. 왕복 시간표 조회
  @GetMapping("/round-trip")
  public ResponseEntity<CommonResponse<List<TimetableResponseDto>>> getRoundTripTimetables(
      @RequestParam int departureStationId,
      @RequestParam int arrivalStationId,
      @RequestParam TransportType transportType
  ) {
    ResponseWithStatus<List<TimetableResponseDto>> response =
        timetableService.getRoundTripTimetablesFiltered(departureStationId, arrivalStationId, transportType);

    return ResponseEntity.status(response.getStatus()).body(response.getBody());
  }

  // 2. 지역 기준 역 목록 조회
  @GetMapping("/stations/region/{regionId}")
  public ResponseEntity<CommonResponse<List<StationResponseDto>>> getStationsByRegion(
      @PathVariable int regionId) {

    ResponseWithStatus<List<StationResponseDto>> response = stationService.getStationsByRegion(regionId);
    return ResponseEntity.status(response.getStatus()).body(response.getBody());
  }
}
