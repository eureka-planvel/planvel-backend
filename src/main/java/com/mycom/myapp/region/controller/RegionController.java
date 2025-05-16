package com.mycom.myapp.region.controller;

import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.region.dto.RegionResponseDto;
import com.mycom.myapp.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<RegionResponseDto>>> getRegions() {
        ResponseWithStatus<List<RegionResponseDto>> response = regionService.getAllRegions();
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }
}
