package com.mycom.myapp.accommodation.service;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;

import java.util.List;

public interface AccommodationService {
    List<AccommodationResponseDto> getAccommodationsByRegion(int regionId);
}
