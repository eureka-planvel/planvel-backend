package com.mycom.myapp.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private int id;
    private String name;
    private String email;
    private String profileImg;
}
