package com.mycom.myapp.spot.service;

import com.mycom.myapp.spot.dto.SpotResponseDto;

import java.util.List;

public interface SpotService {
    List<SpotResponseDto> getSpotsByRegion(int regionId);
}
