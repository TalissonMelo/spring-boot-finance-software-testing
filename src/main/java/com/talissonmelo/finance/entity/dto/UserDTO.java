package com.talissonmelo.finance.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {

	private String name;
	private String email;
	private String password;
}
