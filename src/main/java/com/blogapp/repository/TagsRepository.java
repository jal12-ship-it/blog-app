package com.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.model.Tags;

public interface TagsRepository extends JpaRepository<Tags, Integer> {

	
	
}
