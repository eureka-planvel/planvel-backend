package com.mycom.myapp.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponseDto {
	private int id;
	private String name;
	private String email;
	private String profileImg;
}
