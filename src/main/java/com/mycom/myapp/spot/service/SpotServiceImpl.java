package com.mycom.myapp.spot.service;

import com.mycom.myapp.spot.dto.SpotResponseDto;
import com.mycom.myapp.spot.entity.Spot;
import com.mycom.myapp.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService{
    private final SpotRepository spotRepository;

    public List<SpotResponseDto> getSpotsByRegion(int regionId) {
        List<Spot> spots = spotRepository.findAllByRegionIdWithRegion(regionId);

        return spots.stream()
                .map(spot -> SpotResponseDto.builder()
                        .id(spot.getId())
                        .region(spot.getRegion().getName())
                        .spotName(spot.getSpotName())
                        .address(spot.getAddress())
                        .type(spot.getType())
                        .imageUrl(spot.getImageUrl())
                        .thumbnailUrl(spot.getThumbnailUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
