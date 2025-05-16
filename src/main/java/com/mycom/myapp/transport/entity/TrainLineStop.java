package com.mycom.myapp.transport.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "train_line_stop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainLineStop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "line_id")
  private TrainLine line;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "station_id")
  private Station station;

  private int stopOrder;
}