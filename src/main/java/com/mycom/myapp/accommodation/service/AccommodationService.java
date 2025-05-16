package com.mycom.myapp.accommodation.service;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;

import com.mycom.myapp.common.response.ResponseWithStatus;
import java.util.List;
import org.springframework.data.domain.Page;

public interface AccommodationService {



    ResponseWithStatus<AccommodationResponseDto> getAccommodationById(int id);

    ResponseWithStatus<Page<AccommodationResponseDto>> getAccommodationsByRegion(int regionId, int page, int size);
}
