package com.mycom.myapp.plan.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class PlanSaveRequestDto {
  private TravelInfo travelInfo;
  private Map<String, List<ScheduleData>> scheduleData;

  @Data
  public static class TravelInfo {
    private Integer departureId;
    private String departureName;
    private Integer arrivalId;
    private String arrivalName;
    private Station departureStation;
    private Station arrivalStation;
    private Schedule departureSchedule;
    private Schedule returnSchedule;
    private String transport;
    private Accommodation accommodation;
    private LocalDate startDate;
    private LocalDate endDate;

    @Data
    public static class Station {
      private Integer id;
      private String name;
    }

    @Data
    public static class Schedule {
      private Integer id;
    }

    @Data
    public static class Accommodation {
      private Integer id;
      private String name;
      private Boolean isHotel;
    }
  }

  @Data
  public static class ScheduleData {
    private Integer id;
    private String spotName;
    private String regionName;
    private String address;
    private String type;
    private String imageUrl;
    private String thumbnailUrl;
  }
}