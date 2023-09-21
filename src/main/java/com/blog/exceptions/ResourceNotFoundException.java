package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourseName;
	String fieldName;
	int fieldValue;
	String feildValueString;
	
	public ResourceNotFoundException(String resourseName, String fieldName, int fieldValue) {
		super(String.format("%s not found with this name %s : %s", resourseName, fieldName, fieldValue));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourseName, String fieldName, String feildValueString) {
		super(String.format("%s not found with this name %s : %l", resourseName, fieldName, feildValueString));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.feildValueString = feildValueString;
	}
	
	
	
}
