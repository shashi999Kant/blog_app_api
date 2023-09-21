package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payLoads.ApiResponse;
import com.blog.payLoads.CategoryDto;
import com.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService catgoryService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto) {
		CategoryDto createCategory = this.catgoryService.createCategory(catDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	// update
    @PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto, @PathVariable Integer catId) {
		CategoryDto updatedCategory = this.catgoryService.updateCategory(catDto, catId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		this.catgoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully", false), HttpStatus.OK);
	}

	// get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {

		CategoryDto singleCategory = this.catgoryService.getSingleCategory(catId);

		return new ResponseEntity<CategoryDto>(singleCategory, HttpStatus.OK);

	}
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> categories = this.catgoryService.getCategories();		
		
		return ResponseEntity.ok(categories);
	}

}
