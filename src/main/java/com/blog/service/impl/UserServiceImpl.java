package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.blog.exceptions.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.payLoads.UserDto;
import com.blog.repository.UserRepo;
import com.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDTO) {
		
		User  user=this.dtoToUser(userDTO);
		User saveUser = this.userRepo.save(user);
		return this.userToDto(saveUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userDTO, Integer userId) {

		User user=this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User" , "id" , userId));
		
		user.setAbout(userDTO.getAbout());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setName(userDTO.getName());	
		
		User updatedUser = this.userRepo.save(user);
		UserDto userToDto = this.userToDto(updatedUser);
		
		return userToDto;
		
		
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user=this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User" , "id" , userId));
		
		return this.userToDto(user);
		
		
	}

	@Override
	public List<UserDto> getAllUser() {

		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return userDto;

	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" , "id" , userId));
		
		userRepo.delete(user);		
		
	}
	
	private User dtoToUser(UserDto userDto)
	{
		
		User mappedUser = this.modelMapper.map(userDto, User.class);
		
	//	User nUser=new User();		
//		nUser.setAbout(user.getAbout());
//		nUser.setEmail(user.getEmail());
//		nUser.setId(user.getId());
//		nUser.setPassword(user.getPassword());
//		nUser.setName(user.getName());
		
		return mappedUser;		
	}
	
	private UserDto userToDto(User user)
	{
		return this.modelMapper.map(user, UserDto.class);
	}

}
