package com.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payLoads.PostDto;
import com.blog.payLoads.PostResponse;
import com.blog.repository.CategoryRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(cat);
		post.setUser(user);

		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);

	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub

		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Posts", "post id", postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse	 getAllPosts(Integer pageNo,Integer pageSize ,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("des"))
		{
			sort=Sort.by(sortBy).descending();
		}
		else
		{
			sort=Sort.by(sortBy).ascending();
		}
			
		
		Pageable p=PageRequest.of(pageNo, pageSize,sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post> posts = pagePost.getContent();

		//List<Post> posts = this.postRepo.findAll();
		List<PostDto> postsDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
	PostResponse postResponse=new PostResponse();
	postResponse.setContent(postsDto);
	postResponse.setPageNo(pagePost.getNumber());
	postResponse.setPageSize(pagePost.getSize());
	postResponse.setTotalElements(pagePost.getTotalElements());
	postResponse.setTotalPages(pagePost.getTotalPages());
	postResponse.setLastPage(pagePost.isLast());
	

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer pId) {

		Post post = this.postRepo.findById(pId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", pId));

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategoriry(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDto;

	}

	@Override
	public List<PostDto> searchPost(String keywords) {
		
//		List<Post> posts = this.postRepo.findByTitleContaining("%"+keywords+"%");
		List<Post> posts = this.postRepo.findByTitleContaining(keywords);
		List<PostDto> postDto = posts.stream()
				.map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;

	}

}
