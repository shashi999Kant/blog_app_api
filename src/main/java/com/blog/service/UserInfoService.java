package com.blog.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.payLoads.UserDto;
import com.blog.repository.UserRepo;


@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserRepo repository;
	
	@Autowired
	private ModelMapper mapper;

	@Lazy
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userDetail = repository.findByName(username);

		// Converting userDetail to UserDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	public String addUser(UserDto userInfo) {
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		repository.save(mapper.map(userInfo, User.class));
		return "User Added Successfully";
	}


}
