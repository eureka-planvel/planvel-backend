package com.mycom.myapp.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegisterRequestDto {
	private int id;
	private String name;
	private String email;
	private String password;
}
