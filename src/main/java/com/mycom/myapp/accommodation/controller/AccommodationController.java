package com.mycom.myapp.accommodation.controller;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;
import com.mycom.myapp.accommodation.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stay")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/{region_id}")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationByRegion(@PathVariable("region_id") int regionId) {
        List<AccommodationResponseDto> accommodations = accommodationService.getAccommodationsByRegion(regionId);
        return ResponseEntity.ok(accommodations);
    }
}
