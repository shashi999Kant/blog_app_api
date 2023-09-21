package com.blog.exceptions;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payLoads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourseNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String msg=ex.getMessage();
		ApiResponse apir=new ApiResponse(msg , false);
		
		return new ResponseEntity<ApiResponse>(apir , HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex)
	{
		Map<String, String> resp = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String msg = error.getDefaultMessage();
			resp.put(fieldName, msg);
		});
		
		return new ResponseEntity<Map<String,String>>(resp , HttpStatus.BAD_REQUEST);
	}

}
