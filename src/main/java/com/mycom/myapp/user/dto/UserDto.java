package com.mycom.myapp.user.dto;

import com.mycom.myapp.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserDto {
  private final int id;
  private final String email;
  private final String name;
  private final String profileImg;

  public static UserDto fromEntity(User user) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .profileImg(user.getProfileImg())
        .build();
  }
}