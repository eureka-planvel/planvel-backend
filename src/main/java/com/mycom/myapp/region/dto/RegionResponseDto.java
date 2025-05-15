package com.mycom.myapp.region.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionResponseDto {
    private int id;
    private String name;
}
