package com.blogapp.service;

import com.blogapp.model.Comment;
import com.blogapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Integer id) {
        return commentRepository.findById(id);
    }

    public void deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
    }
}
