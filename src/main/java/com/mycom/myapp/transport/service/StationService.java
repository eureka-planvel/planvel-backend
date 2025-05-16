package com.mycom.myapp.transport.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.transport.dto.StationResponseDto;
import java.util.List;

public interface StationService {

  ResponseWithStatus<List<StationResponseDto>> getStationsByRegion(int regionId);
}
