package com.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blogapp.model.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
	
	@Query(value = "select * from posts p, tags t where p.author like %:search% or p.content like %:search% or p.title like "
			+ "%:search% or p.excerpt like %:search% or t.name like %:search%", nativeQuery = true)
	List<Posts> findByKeyword(@Param("search") String search);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "update posts p1 set p1.excerpt = left(p1.content, 100) where p1.id = :id", nativeQuery = true)
	void createExcerpt(@Param("id") Integer id);
}