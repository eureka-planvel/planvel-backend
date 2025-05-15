package com.mycom.myapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import com.mycom.myapp.user.entity.User;

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