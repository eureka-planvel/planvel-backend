package com.mycom.myapp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterDto {
	private int id;
	private String name;
	private String email;
	private String password;
	private String profileImg;
}
