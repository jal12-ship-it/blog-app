package com.blogapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blogapp.model.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
	
	@Query(value = "select * "
			+ "from posts p "
			+ "left join posts_tags on (p.id = posts_id) "
			+ "left join tags on (tags.id = tags_id) "
			+ "where p.author like %:search% or p.content like %:search% or p.title like %:search% "
			+ "or name like %:search%", nativeQuery = true)
	Page<Posts> findByKeyword(Pageable pageable, @Param("search") String search);
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "update posts p1 set p1.excerpt = left(p1.content, 100) where p1.id = :id", nativeQuery = true)
	void createExcerpt(@Param("id") Integer id);

	@Query(value = "select distinct author from posts", nativeQuery = true)
	List<String> findDistictAuthor();

	@Query(value = "select * from posts "
			+ "left join posts_tags on (posts.id = posts_id) "
			+ "left join tags on (tags.id = tags_id) "
			+ "where author in :authorName "
			+ "and name in :tagName", nativeQuery = true)
	Page<Posts> findByAuthor(List<String> authorName, List<String> tagName, Pageable pageable);
}