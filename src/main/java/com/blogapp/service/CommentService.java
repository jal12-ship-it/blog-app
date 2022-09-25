package com.blogapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.model.Comments;
import com.blogapp.repository.CommentsRepository;

@Service
public class CommentService {

	@Autowired
	private CommentsRepository commentsRepository;

	public void saveComments(Comments comment) {
		commentsRepository.save(comment);
	}

	public List<Comments> getAllComments() {
		return commentsRepository.findAll();
	}

	public Optional<Comments> getCommentById(Integer id) {
		return commentsRepository.findById(id);
	}

	public void deleteCommentById(Integer id) {
		commentsRepository.deleteById(id);
	}
}
