package com.blogapp.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
	@Query(value="update post p1 set p1.excerpt = left(p1.content, 100) where p1.id = :id", nativeQuery = true)
	void createExcerpt(@Param("id") Integer id);

	Set<Post> findByIsPublished(Boolean isPublished);

	@Query("select distinct p from Post p left join p.tag tag " +
			"where p.title=:search " +
			"or p.content=:search " +
			"or p.author=:search " +
			"or tag.name=:search " +
			"and p.isPublished = :isPublished")
	Set<Post> findByKeyword(String search, Boolean isPublished);

	@Query("select author from Post where isPublished = :isPublished")
	Set<String> findAuthorByIsPublished(Boolean isPublished);

	@Query("select p from Post p inner join p.tag tag " +
			"where p.author in ?1 and tag.name in ?2 and p.publishedAt between ?3 and ?4 " +
			"and p.isPublished=?5")
	Set<Post> findByFilter(List<String> authors, List<String> name, Date publishedAtStart,
							Date publishedAtEnd, Boolean isPublished);


	@Query("select p from Post p left join p.tag tag where p.isPublished = :isPublished and p.author in :authors and tag.name in :name")
	Set<Post> findByFilters(List<String> authors, List<String> name, Boolean isPublished);


}