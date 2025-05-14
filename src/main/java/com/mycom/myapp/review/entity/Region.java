package com.mycom.myapp.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="region")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

}
