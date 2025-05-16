package com.mycom.myapp.plan.entity;

import com.mycom.myapp.spot.entity.type.SpotType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "travel_schedule")
public class TravelSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "travel_plan_id", nullable = false)
  private TravelPlan travelPlan;

  private LocalDate scheduleDate;

  private Integer spotId;
  private String spotName;

  @Enumerated(EnumType.STRING)
  private SpotType spotType;

  private String regionName;
  private String address;

  private String imageUrl;
  private String thumbnailUrl;

  @CreationTimestamp
  private LocalDateTime createdAt;
}