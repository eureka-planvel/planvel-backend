package com.mycom.myapp.accommodation.controller;

import com.mycom.myapp.accommodation.dto.AccommodationResponseDto;
import com.mycom.myapp.accommodation.service.AccommodationService;
import com.mycom.myapp.common.response.CommonResponse;
import com.mycom.myapp.common.response.ResponseWithStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/region/{region_id}")
    public ResponseEntity<CommonResponse<Page<AccommodationResponseDto>>> getAccommodationsByRegion(
        @PathVariable("region_id") int regionId,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "3") int size) {

        ResponseWithStatus<Page<AccommodationResponseDto>> response = accommodationService.getAccommodationsByRegion(regionId, page, size);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<AccommodationResponseDto>> getAccommodationById(@PathVariable("id") int id) {
        ResponseWithStatus<AccommodationResponseDto> response = accommodationService.getAccommodationById(id);
        return ResponseEntity.status(response.getStatus()).body(response.getBody());
    }
}
