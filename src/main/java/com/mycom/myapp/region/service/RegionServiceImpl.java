package com.mycom.myapp.region.service;

import com.mycom.myapp.region.dto.RegionResponseDto;
import com.mycom.myapp.region.entity.Region;
import com.mycom.myapp.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{

    private final RegionRepository regionRepository;

    public List<RegionResponseDto> getAllRegions() {
        List<Region> regions = regionRepository.findAll();

        return regions.stream()
                .map(region -> RegionResponseDto.builder()
                        .id(region.getId())
                        .name(region.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
