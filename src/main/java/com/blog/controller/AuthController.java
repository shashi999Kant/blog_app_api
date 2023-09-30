package com.blog.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entities.AuthRequest;
import com.blog.entities.User;
import com.blog.payLoads.UserDto;
import com.blog.payLoads.jwtAuthResponce;
import com.blog.repository.UserRepo;
import com.blog.service.JwtService;
import com.blog.service.UserInfoService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserInfoService service;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	
	@PostMapping("/addNewUser")
	public String addNewUser(@Valid @RequestBody UserDto userInfo) {
		return service.addUser(userInfo);
	}
	
	
	@PostMapping("/generateToken")
	public ResponseEntity<jwtAuthResponce> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			String generateToken = jwtService.generateToken(authRequest.getUsername());
			User user = userRepo.findByEmail(authRequest.getUsername()).get();
			
			jwtAuthResponce responce=new jwtAuthResponce();
			responce.setToken(generateToken);
			responce.setUser(mapper.map(user, UserDto.class));
			
			return new ResponseEntity<jwtAuthResponce>(responce,HttpStatus.OK);
			
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}



}
