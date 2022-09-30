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

import com.blogapp.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Transactional
	@Modifying(clearAutomatically=true)
	@Query(value = "update post p1 set p1.excerpt = left(p1.content, 100) where p1.id = :id", nativeQuery = true)
	void createExcerpt(@Param("id") Integer id);

	Page<Post> findByIsPublished(Boolean isPublished, Pageable pageable);

	@Query("select distinct p from Post p left join p.tag tag " +
			"where p.title like %:search% " +
			"or p.content like %:search% " +
			"or p.author like %:search% " +
			"or tag.name like %:search%")
	Page<Post> findByKeyword(String search, Pageable pageable);

	@Query(value = "select distinct author from post", nativeQuery = true)
	List<String> findDistinctAuthor();

	Page<Post> findDistinctByAuthorInAndTag_NameIn(List<String> authors, List<String> names, Pageable pageable);

}