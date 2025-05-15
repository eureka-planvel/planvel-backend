package com.mycom.myapp.spot.controller;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;
import com.mycom.myapp.spot.dto.SpotResponseDto;
import com.mycom.myapp.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spot")
@RequiredArgsConstructor
public class SpotController {
    private final SpotService spotService;

    @GetMapping("/{region_id}")
    public ResponseEntity<List<SpotResponseDto>> getSpotsByRegion(@PathVariable("region_id") int regionId) {
        List<SpotResponseDto> spots = spotService.getSpotsByRegion(regionId);
        return ResponseEntity.ok(spots);
    }

}
