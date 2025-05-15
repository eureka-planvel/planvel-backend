package com.mycom.myapp.region.controller;

import com.mycom.myapp.region.dto.RegionResponseDto;
import com.mycom.myapp.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionResponseDto>> getRegions() {
        List<RegionResponseDto> regions = regionService.getAllRegions();
        return ResponseEntity.ok(regions);
    }
}
