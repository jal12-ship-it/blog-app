package com.blogapp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapp.model.Post;
import com.blogapp.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	@Query(value="select distinct name from tag", nativeQuery = true)
	List<String> findAllTags();

	@Query (value = "select * from post "
			+ "left join post_tag on (post.id = post_id) "
			+ "left join tag on (tag.id = tag_id) "
			+ "where name = :name ", nativeQuery=true)
	List<Post> findByName(String name, Pageable pageable);

}
