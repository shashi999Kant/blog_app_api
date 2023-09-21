package com.blog.service;


import java.util.List;

import com.blog.payLoads.CategoryDto;

public interface CategoryService {
	
	//create
	 CategoryDto createCategory(CategoryDto catDto);
	
	//update
	 CategoryDto updateCategory(CategoryDto catDto,Integer catId);
	
	//delete
	 void deleteCategory(Integer catId);
	
	//get all
	 List<CategoryDto> getCategories();
	
	//get single 
	 CategoryDto getSingleCategory(Integer id);

}
