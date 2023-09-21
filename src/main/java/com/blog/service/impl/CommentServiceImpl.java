package com.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;

import com.blog.payLoads.CommentDto;
import com.blog.repository.CommentsRepo;
import com.blog.repository.PostRepo;
import com.blog.repository.UserRepo;
import com.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	
	 @Autowired private UserRepo userRepo;
	 

	@Autowired
	private CommentsRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId,Integer user_id) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		 User user = this.userRepo.findById(user_id) .orElseThrow(() -> new
		 ResourceNotFoundException("User", "user id", user_id));
		 
		

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);

		Comment savedComment = this.commentRepo.save(comment);

		return this.modelMapper.map(savedComment, CommentDto.class);

	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));

		this.commentRepo.delete(comment);

	}


	/*
	 * @Override public List<CommentDto> getComment() {
	 * 
	 * List<Comment> comments = this.commentRepo.findAll();
	 * 
	 * List<CommentDto> allComments = comments.stream().map((comment) ->
	 * this.modelMapper.map(comment,
	 * CommentDto.class)).collect(Collectors.toList());
	 * 
	 * return allComments;
	 * 
	 * }
	 */
	 

}
