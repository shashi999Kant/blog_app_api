package com.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payLoads.ApiResponse;
import com.blog.payLoads.CommentDto;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,@PathVariable Integer userId) {
		CommentDto createComment = this.commentService.createComment(commentDto, postId,userId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);

	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully!", true), HttpStatus.OK);

	}

	/*
	 * @GetMapping("/comments/") public ResponseEntity<List<CommentDto>>
	 * getComment() { List<CommentDto> comment = this.commentService.getComment();
	 * return ResponseEntity.ok(comment); }
	 */
}
