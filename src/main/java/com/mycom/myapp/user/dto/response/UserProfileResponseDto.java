package com.mycom.myapp.user.dto.response;

import com.mycom.myapp.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponseDto {
    private int id;
    private String name;
    private String email;
    private String profileImg;

    public static UserProfileResponseDto from(User user) {
        return UserProfileResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .profileImg(user.getProfileImg())
            .build();
    }
}
