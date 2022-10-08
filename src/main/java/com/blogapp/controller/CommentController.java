package com.blogapp.controller;

import com.blogapp.model.Comment;
import com.blogapp.model.MyUserDetails;
import com.blogapp.model.Post;
import com.blogapp.service.CommentService;
import com.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RequestMapping("/comment")
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @PostMapping("/saveComment")
    public String saveComment(@ModelAttribute("comment") Comment comment, @ModelAttribute("postId") Integer id) {
        Optional<Post> optional = postService.getPostById(id);
        if (optional.isPresent()) {
            comment.setPost(optional.get());
        } else {
            throw new RuntimeException("Post not found with id::" + id);
        }

        commentService.saveComment(comment);

        return "redirect:/showPost/" + id;
    }

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping("/updateComment/{id}")
    public String updateComment(@PathVariable(value = "id") Integer id, Model model, @AuthenticationPrincipal MyUserDetails user) {
        Optional<Comment> optional = commentService.getCommentById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Comment not found with id::" + id);
        else if(!commentService.isAuthorized(id, user))
            throw new RuntimeException("You are not authorized to execute this operation");

        model.addAttribute("comment", optional.get());

        return "updateComment";
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable(value = "id") Integer id, @AuthenticationPrincipal MyUserDetails user) {
        if(!commentService.isAuthorized(id, user))
            throw new RuntimeException("You are not authorized to execute this operation");

        commentService.deleteCommentById(id);
        return "redirect:/";
    }


}
