package com.mycom.myapp.region.service;

import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.dto.RegionResponseDto;

import java.util.List;

public interface RegionService {

    ResponseWithStatus<List<RegionResponseDto>> getAllRegions();
}
