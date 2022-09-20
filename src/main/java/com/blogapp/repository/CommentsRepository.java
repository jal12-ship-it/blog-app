package com.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

}
