package com.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blogapp.model.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
	
	@Query(value = "select * from posts p where p.author like %:keyword% or p.content like %:keyword% or p.title like "
			+ "%:keyword% or p.excerpt like %:keyword%", nativeQuery = true)
	List<Posts> findByKeyword(@Param("keyword") String keyword);
}
