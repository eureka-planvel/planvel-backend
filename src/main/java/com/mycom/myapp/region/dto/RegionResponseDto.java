package com.mycom.myapp.region.dto;

import com.mycom.myapp.region.entity.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionResponseDto {
    private int id;
    private String name;

    public static RegionResponseDto from(Region region) {
        return RegionResponseDto.builder()
            .id(region.getId())
            .name(region.getName())
            .build();
    }
}
