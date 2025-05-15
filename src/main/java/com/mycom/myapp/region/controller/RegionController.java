package com.mycom.myapp.region.controller;

import com.mycom.myapp.region.dto.RegionResponseDto;
import com.mycom.myapp.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
@Tag(name = "Region", description = "지역 관련 API")
public class RegionController {

    private final RegionService regionService;

    @Operation(summary = "전체 지역 목록 조회", description = "등록된 모든 지역 목록을 반환합니다.")
    @GetMapping
    public ResponseEntity<List<RegionResponseDto>> getRegions() {
        List<RegionResponseDto> regions = regionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }
}
