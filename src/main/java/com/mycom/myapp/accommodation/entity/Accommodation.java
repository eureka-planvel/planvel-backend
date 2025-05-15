package com.mycom.myapp.accommodation.entity;

import com.mycom.myapp.region.entity.Region;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accommodations")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    private String name;

    private String address;

    private String pricePerNight;

    private String type;

    private String imageUrl;

    private String thumbnailUrl;
}
