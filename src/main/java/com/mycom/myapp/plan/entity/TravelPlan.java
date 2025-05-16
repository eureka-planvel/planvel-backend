package com.mycom.myapp.plan.entity;

import com.mycom.myapp.plan.entity.type.AccommodationType;
import com.mycom.myapp.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "travel_plan")
public class TravelPlan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true, length = 8)
  private String code;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private Integer departureId;
  private String departureName;

  private Integer arrivalId;
  private String arrivalName;

  private Integer departureStationId;
  private String departureStationName;

  private Integer arrivalStationId;
  private String arrivalStationName;

  private Integer departureScheduleId;
  private Integer returnScheduleId;

  private String transport;

  private Integer accommodationId;
  private String accommodationName;

  @Enumerated(EnumType.STRING)
  private AccommodationType accommodationType;

  private LocalDate startDate;
  private LocalDate endDate;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TravelSchedule> schedules = new ArrayList<>();
}