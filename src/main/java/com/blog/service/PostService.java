package com.blog.service;

import java.util.List;


import com.blog.payLoads.PostDto;
import com.blog.payLoads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer userId , Integer categoryId);
	
	PostDto updatePost(PostDto postDto , Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPosts(Integer pageNo , Integer pageSize ,String sortBy,String sortDir);
	
	PostDto getPostById(Integer id);
	
	//get all post by category
	List<PostDto> getPostByCategoriry(Integer categoryId);
	
	//get all post related to user
	List<PostDto> getPostByUser(Integer userId);
	
	PostResponse searchPost(String keywords,Integer pageNo , Integer pageSize ,String sortBy,String sortDir);
	

}
