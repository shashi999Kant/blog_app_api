package com.blog.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payLoads.ApiResponse;
import com.blog.payLoads.PostDto;
import com.blog.payLoads.PostResponse;
import com.blog.service.FileService;
import com.blog.service.PostService;

import org.springframework.util.StreamUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto , 
			@PathVariable Integer userId ,
			@PathVariable Integer categoryId
			)
	{
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost , HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser , HttpStatus.OK);
	}
	
	
	@GetMapping("/category/{category}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer category)
	{
		List<PostDto> postByCategoriry = this.postService.getPostByCategoriry(category);
		return new ResponseEntity<List<PostDto>>(postByCategoriry , HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNo,
			@RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
			@RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
			@RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir
			)
	{
		PostResponse postResponse = this.postService.getAllPosts(pageNo, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse , HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		 PostDto postById = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById , HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post successfully deleted" , true);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable Integer postId)
	{
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost , HttpStatus.OK);

	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<PostResponse> searchPostByTitle(@PathVariable("keywords") String keyword,
			@RequestParam(value = "pageNo" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNo,
			@RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
			@RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
			@RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir
			)
	{
		PostResponse searchPost = this.postService.searchPost(keyword, pageNo ,  pageSize , sortBy, sortDir);
		return new ResponseEntity<PostResponse>(searchPost , HttpStatus.OK);
	}
	
	
	//post image upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException
	{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.upoloadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
		
	}
	
	
	
	//method to serve file
	@GetMapping(value = "/posts/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException
	{
		InputStream resource=  this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
