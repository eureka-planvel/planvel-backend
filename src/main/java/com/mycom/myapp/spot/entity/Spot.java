package com.mycom.myapp.spot.entity;

import com.mycom.myapp.region.entity.Region;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "spots")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name="spot_name", nullable = false)
    private String spotName;

    @Column(nullable = false)
    private String address;

    private String type;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="thumbnail_url")
    private String thumbnailUrl;
}
