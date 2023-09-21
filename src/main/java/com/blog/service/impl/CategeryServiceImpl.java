package com.blog.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payLoads.CategoryDto;
import com.blog.repository.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CategeryServiceImpl implements CategoryService{
	
    @Autowired
	private CategoryRepo categoryRepo;	
    
    @Autowired
    private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto catDto) {

		Category cat = this.modelMapper.map(catDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addedCat, CategoryDto.class);
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto catDto, Integer catId) {
		
		Category cat = this.categoryRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("category", "category Id", catId));		
		
		cat.setCategorytitle(catDto.getCategorytitle());
		cat.setCategoryDesc(catDto.getCategoryDesc());
		Category updatedCat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedCat, CategoryDto.class);
		
	}

	@Override
	public void deleteCategory(Integer catId) {

		Category cat = this.categoryRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("category", "category Id", catId));		
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public List<CategoryDto> getCategories() {
		
		List<Category> categories = this.categoryRepo.findAll();
		
		
		List<CategoryDto> allCategories = categories.stream().map( (category) -> 
			this.modelMapper.map(category, CategoryDto.class)
		).collect(Collectors.toList());
		
		return allCategories;
		
		
		
	}

	@Override
	public CategoryDto getSingleCategory(Integer id) {

		Category cat = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "category Id", id));		
		return this.modelMapper.map(cat, CategoryDto.class);

	}

	
}
