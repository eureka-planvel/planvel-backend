package com.mycom.myapp.spot.controller;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import com.mycom.myapp.spot.dto.SpotResponseDto;
import com.mycom.myapp.spot.entity.type.SpotType;
import com.mycom.myapp.spot.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spot")
@RequiredArgsConstructor
public class SpotController {
    private final SpotService spotService;

    @GetMapping("/region/{region_id}")
    public ResponseEntity<CommonResponse<List<SpotResponseDto>>> getSpotsByRegionAndType(
        @PathVariable("region_id") int regionId,
        @RequestParam("type") SpotType type,
        @RequestParam(value = "page", defaultValue = "1") int page) {

        ResponseWithStatus<List<SpotResponseDto>> response = spotService.getSpotsByRegionAndType(regionId, type, page, 9);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<SpotResponseDto>> getSpotDetail(@PathVariable("id") int id ){
        ResponseWithStatus<SpotResponseDto> response = spotService.getSpotDetail(id);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }

}
