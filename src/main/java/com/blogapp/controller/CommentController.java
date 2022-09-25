package com.blogapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blogapp.model.Comments;
import com.blogapp.model.Posts;
import com.blogapp.service.CommentService;
import com.blogapp.service.PostService;

@RequestMapping("/comment")
@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostService postService;

	@GetMapping("/updateComment/{id}")
	public String updateComment(@PathVariable(value="id") Integer id, Model model, Comments comment) {
		Optional<Comments> optional =  commentService.getCommentById(id);
		
		if(optional.isPresent()) {
			comment = optional.get();
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		System.out.println(comment.getMessage());
		model.addAttribute("comment", comment);
		
		return "updateComment";
	}
	
	@PostMapping("/saveComment")
	public String saveComment(@ModelAttribute("comment") Comments comments, @ModelAttribute("postId") Integer id, Posts post) {
		Optional<Posts> optional = postService.getPostById(id);
		if(optional.isPresent()) {
			post = optional.get();
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		
		comments.setPost(post);
		commentService.saveComments(comments);
		
		return "redirect:/showPost/" + post.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String deleteComment(@PathVariable(value="id") Integer id) {
		
		commentService.deleteCommentById(id);	
		return "redirect:/";
	}
	
}
