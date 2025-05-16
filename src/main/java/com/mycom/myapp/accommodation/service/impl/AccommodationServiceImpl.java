package com.mycom.myapp.accommodation.service.impl;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;
import com.mycom.myapp.accommodation.entity.Accommodation;
import com.mycom.myapp.accommodation.repository.AccommodationRepository;
import com.mycom.myapp.accommodation.service.AccommodationService;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Override
    public ResponseWithStatus<Page<AccommodationResponseDto>> getAccommodationsByRegion(int regionId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Accommodation> accommodations = accommodationRepository.findAllByRegionIdWithRegion(regionId, pageRequest);

        Page<AccommodationResponseDto> dtoPage = accommodations.map(AccommodationResponseDto::from);

        return ResponseWithStatus.ok(CommonResponse.success(dtoPage, "숙소 목록 조회 성공"));
    }

    @Override
    public ResponseWithStatus<AccommodationResponseDto> getAccommodationById(int id) {
        Accommodation accommodation = accommodationRepository.findByIdWithRegion(id)
            .orElseThrow(() -> new IllegalArgumentException("숙소를 찾을 수 없습니다."));
        return ResponseWithStatus.ok(CommonResponse.success(AccommodationResponseDto.from(accommodation), "숙소 상세 조회 성공"));
    }
}
