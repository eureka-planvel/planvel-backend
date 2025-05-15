package com.mycom.myapp.spot.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpotResponseDto {
    private int id;
    private String region;
    private String spotName;
    private String address;
    private String type;
    private String imageUrl;
    private String thumbnailUrl;
}
