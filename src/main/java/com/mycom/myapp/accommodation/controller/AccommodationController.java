package com.mycom.myapp.accommodation.controller;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;
import com.mycom.myapp.accommodation.service.AccommodationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stay")
@RequiredArgsConstructor
@Tag(name = "Accommodations", description = "숙소 관련 API")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @Operation(summary = "지역별 숙소 조회", description = "지역 ID를 이용해 해당 지역의 숙소 리스트를 조회합니다.")
    @GetMapping("/{region_id}")
    public ResponseEntity<List<AccommodationResponseDto>> getAccommodationsByRegion(@PathVariable("region_id") int regionId) {
        List<AccommodationResponseDto> accommodations = accommodationService.getAccommodationsByRegion(regionId);
        return ResponseEntity.ok(accommodations);
    }
}
