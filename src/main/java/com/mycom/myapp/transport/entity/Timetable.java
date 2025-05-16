package com.mycom.myapp.transport.entity;

import com.mycom.myapp.transport.entity.type.TransportType;

import jakarta.persistence.*;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timetable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timetable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Enumerated(EnumType.STRING)
  private TransportType transportType; // BUS, TRAIN

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "departure_station_id")
  private Station departureStation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "arrival_station_id")
  private Station arrivalStation;

  private LocalTime departureTime;
  private int durationMin;
  private int price;
  private String transportNumber;
}