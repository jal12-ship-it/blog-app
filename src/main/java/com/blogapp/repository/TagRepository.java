package com.blogapp.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapp.model.Post;
import com.blogapp.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	@Query("select distinct t.name from Tag t left join t.post post " +
			"where post.isPublished = :isPublished")
	Set<String> findTagsByIsPublished(Boolean isPublished);

	@Query("select name from Tag")
	List<String> findName();
}
