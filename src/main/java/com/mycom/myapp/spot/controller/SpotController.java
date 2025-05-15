package com.mycom.myapp.spot.controller;

import com.mycom.myapp.spot.dto.SpotResponseDto;
import com.mycom.myapp.spot.service.SpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Spots", description = "관광지 관련 API")
public class SpotController {
    private final SpotService spotService;

    @Operation(summary = "지역별 관광지 조회", description = "지역 ID를 이용해 해당 지역의 관광지 리스트를 조회합니다.")
    @GetMapping("/{region_id}")
    public ResponseEntity<List<SpotResponseDto>> getSpotsByRegion(@PathVariable("region_id") int regionId) {
        List<SpotResponseDto> spots = spotService.getSpotsByRegion(regionId);
        return ResponseEntity.ok(spots);
    }

}
