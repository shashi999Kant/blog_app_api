package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
//  @Query("select p from post p where p.title like :key")	
//	List<Post> findByTitleContaining(@Param("key") String title);	
	
	
	List<Post> findByTitleContaining(String title);	

}
