package com.mycom.myapp.accommodation.dto;

import com.mycom.myapp.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationResponseDto {
    private int id;
    private String regionName;
    private String name;
    private String address;
    private String pricePerNight;
    private boolean isHotel;
    private String imageUrl;
    private String thumbnailUrl;

    public static AccommodationResponseDto from(Accommodation accommodation) {
        return AccommodationResponseDto.builder()
            .id(accommodation.getId())
            .regionName(accommodation.getRegion().getName())
            .name(accommodation.getName())
            .address(accommodation.getAddress())
            .pricePerNight(accommodation.getPricePerNight())
            .isHotel(accommodation.isHotel())
            .imageUrl(accommodation.getImageUrl())
            .thumbnailUrl(accommodation.getThumbnailUrl())
            .build();
    }
}