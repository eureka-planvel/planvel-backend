package com.mycom.myapp.spot.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.spot.dto.SpotResponseDto;

import com.mycom.myapp.spot.entity.type.SpotType;
import java.util.List;

public interface SpotService {
  ResponseWithStatus<List<SpotResponseDto>> getSpotsByRegionAndType(int regionId, SpotType type, int page, int size);

  ResponseWithStatus<SpotResponseDto> getSpotDetail(int id);
}
