package com.blog.service;


import java.util.List;

import com.blog.payLoads.CommentDto;

public interface CommentService {
	
   public CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
   
   public void deleteComment(Integer commentId);
   
	/*
	 * public List<CommentDto> getComment();
	 */
}
