package com.blog.payLoads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	
	private int id;
	
	@NotEmpty
	@Size(min = 4 , message = "user must be minimum of 4 charactors")
	private String name;
	
	
	@Column(unique = true)
	@Email(message = "email address is not valid!!")
	@NotEmpty(message = "Email required!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10 ,message = "password must be min of 3 chars and max of 10 chars !! ")
	private String password;
	
	@NotEmpty
	private String about;
	
	
	private String role;	
	
	/*
	 * private String imageName;
	 */
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	
	@JsonProperty
	public void setPassword(String password) {
		 this.password=password;
	}
	
	
	

}
