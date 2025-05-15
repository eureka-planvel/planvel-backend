package com.mycom.myapp.region.service.impl;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.dto.RegionResponseDto;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.region.repository.RegionRepository;
import com.mycom.myapp.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public ResponseWithStatus<List<RegionResponseDto>> getAllRegions(){
        List<Region> regions = regionRepository.findAll();

        if (regions.isEmpty()) {
            return ResponseWithStatus.badRequest(CommonResponse.fail("등록된 지역이 없습니다."));
        }

        List<RegionResponseDto> regionDtos = regions.stream()
            .map(RegionResponseDto::from)
            .collect(Collectors.toList());

        return ResponseWithStatus.ok(CommonResponse.success(regionDtos,"지역 목록 조회 성공"));
    }
}
