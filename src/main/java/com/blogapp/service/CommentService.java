package com.blogapp.service;

import com.blogapp.model.Comment;
import com.blogapp.model.MyUserDetails;
import com.blogapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Integer id) {
        return commentRepository.findById(id);
    }

    public void deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
    }

    public void saveAll(Set<Comment> comment) {
        commentRepository.saveAll(comment);
    }

    public Page<Comment> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public boolean isAuthorized(Integer id, MyUserDetails user) {
        return Objects.equals(user.getUsername(), getCommentById(id).get().getPost().getUser().getUsername());
    }
}
