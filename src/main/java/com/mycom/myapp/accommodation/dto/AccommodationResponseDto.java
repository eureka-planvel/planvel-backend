package com.mycom.myapp.accommodation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationResponseDto {
    private int id;
    private String region;
    private String name;
    private String address;
    private String pricePerNight;
    private String type;
    private String imageUrl;
    private String thumbnailUrl;
}
