package com.taylor.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDefault {
	
	public static final String DEFAULT_ROLE = "ROLE_USER";
	
	private UserDefault() {
		
	}
	
}
