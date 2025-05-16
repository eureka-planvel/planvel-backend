package com.mycom.myapp.user.dto;

import com.mycom.myapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfo {
  private final int id;
  private final String email;
  private final String name;
  private final String profileImg;

  public static UserInfo fromEntity(User user) {
    return UserInfo.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .profileImg(user.getProfileImg())
        .build();
  }
}