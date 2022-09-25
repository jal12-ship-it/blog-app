package com.blogapp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapp.model.Posts;
import com.blogapp.model.Tags;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

	@Query(value="select distinct name from tags", nativeQuery = true)
	List<String> findAllTags();

	@Query (value = "select * from posts left join posts_tags on (posts.id = posts_id) left join tags on (tags.id = tags_id) where name = :name ", nativeQuery=true)
	List<Posts> findByName(String name, Pageable pageable);

}
