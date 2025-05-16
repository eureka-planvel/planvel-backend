package com.mycom.myapp.spot.dto;

import com.mycom.myapp.spot.entity.Spot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpotResponseDto {
    private int id;
    private String regionName;
    private String spotName;
    private String address;
    private String type;
    private String imageUrl;
    private String thumbnailUrl;


    public static SpotResponseDto from(Spot spot) {
        return SpotResponseDto.builder()
            .id(spot.getId())
            .regionName(spot.getRegion().getName())
            .spotName(spot.getSpotName())
            .address(spot.getAddress())
            .type(spot.getType().name())
            .imageUrl(spot.getImageUrl())
            .thumbnailUrl(spot.getThumbnailUrl())
            .build();
    }
}
