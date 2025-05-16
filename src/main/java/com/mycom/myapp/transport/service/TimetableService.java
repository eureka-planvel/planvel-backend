package com.mycom.myapp.transport.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.transport.dto.TimetableResponseDto;
import com.mycom.myapp.transport.entity.type.TransportType;
import java.util.List;

public interface TimetableService {

  ResponseWithStatus<List<TimetableResponseDto>> getRoundTripTimetablesFiltered(int departureStationId, int arrivalStationId, TransportType transportType);
}
