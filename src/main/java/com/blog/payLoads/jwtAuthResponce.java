package com.blog.payLoads;

import lombok.Data;

@Data
public class jwtAuthResponce {
	
	private String token;
	private UserDto user;

}
