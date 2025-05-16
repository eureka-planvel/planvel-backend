package com.mycom.myapp.spot.entity.type;

public enum SpotType {
  FOOD("음식"),
  TOURIST("관광지"),
  CAFE("카페");

  private final String displayName;

  SpotType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}