package com.mycom.myapp.transport.service.impl;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.transport.dto.TimetableResponseDto;
import com.mycom.myapp.transport.entity.Timetable;
import com.mycom.myapp.transport.entity.type.TransportType;
import com.mycom.myapp.transport.repository.TimetableRepository;
import com.mycom.myapp.transport.service.TimetableService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {

  private final TimetableRepository timetableRepository;

  @Override
  public ResponseWithStatus<List<TimetableResponseDto>> getRoundTripTimetablesFiltered(
      int departureStationId, int arrivalStationId, TransportType transportType) {

    List<Timetable> timetables = timetableRepository.findRoundTripTimetablesFiltered(departureStationId, arrivalStationId, transportType);

    List<TimetableResponseDto> dtos = timetables.stream()
        .map(TimetableResponseDto::from)
        .collect(Collectors.toList());

    return ResponseWithStatus.ok(CommonResponse.success(dtos, "왕복 시간표 조회 성공"));
  }
}
