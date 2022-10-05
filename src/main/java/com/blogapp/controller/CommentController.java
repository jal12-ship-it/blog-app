package com.blogapp.controller;

import com.blogapp.model.Comment;
import com.blogapp.model.Post;
import com.blogapp.service.CommentService;
import com.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/updateComment/{id}")
    public String updateComment(@PathVariable(value = "id") Integer id, Model model) {


        Optional<Comment> optional = commentService.getCommentById(id);

        if (optional.isPresent()) {
            model.addAttribute("comment", optional.get());
        } else {
            throw new RuntimeException("Comment not found with id::" + id);
        }

        return "updateComment";
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable(value = "id") Integer id) {
        commentService.deleteCommentById(id);
        return "redirect:/";
    }


}
