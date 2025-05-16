package com.mycom.myapp.spot.service.impl;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.spot.dto.SpotResponseDto;
import com.mycom.myapp.spot.entity.Spot;
import com.mycom.myapp.spot.entity.type.SpotType;
import com.mycom.myapp.spot.repository.SpotRepository;
import com.mycom.myapp.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    private final SpotRepository spotRepository;
    @Override
    public ResponseWithStatus<List<SpotResponseDto>> getSpotsByRegionAndType(int regionId, SpotType type, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Spot> spots = spotRepository.findByRegionIdAndType(regionId, type, pageable);

        List<SpotResponseDto> dtos = spots.stream()
            .map(SpotResponseDto::from)
            .collect(Collectors.toList());

        return ResponseWithStatus.ok(CommonResponse.success(dtos, "조회 성공"));
    }

    @Override
    public ResponseWithStatus<SpotResponseDto> getSpotDetail(int id) {
        Spot spot = spotRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스팟입니다."));

        SpotResponseDto dto = SpotResponseDto.from(spot);

        return ResponseWithStatus.ok(CommonResponse.success(dto, "조회 성공"));
    }
}
