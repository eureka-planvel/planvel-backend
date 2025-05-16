package com.mycom.myapp.transport.service.impl;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.transport.dto.StationResponseDto;
import com.mycom.myapp.transport.entity.Station;
import com.mycom.myapp.transport.repository.StationRepository;
import com.mycom.myapp.transport.service.StationService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

  private final StationRepository stationRepository;

  @Override
  public ResponseWithStatus<List<StationResponseDto>> getStationsByRegion(int regionId) {
    List<Station> stations = stationRepository.findByRegionId(regionId);

    List<StationResponseDto> dtos = stations.stream()
        .map(StationResponseDto::from)
        .collect(Collectors.toList());

    return ResponseWithStatus.ok(CommonResponse.success(dtos, "역 목록 조회 성공"));
  }
}
