package com.blogapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.blogapp.model.*;
import com.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.blogapp.service.PostService;
import com.blogapp.service.TagService;

@Controller
public class PostController {

	@Autowired
	private TagService tagService;
	@Autowired
	private PostService postService;
	@Autowired
	private UserRepository userRepository;


	@PostMapping("/savePost")
	public String savePost(@ModelAttribute("post") Post post, String tagString, String publishType, @AuthenticationPrincipal MyUserDetails userDetails) {
		List<String> tagList = new ArrayList<>(Arrays.asList(tagString.split(" ")));
		
		for (String eachTag : tagList) {
			Tag tag = new Tag();
			if(tagService.getTags().contains(eachTag)) {
				post.getTag().add(tag);
			} else {	
			tag.setName(eachTag);
			post.getTag().add(tag);
			}
		}

		post.setUser(userRepository.findByEmail(userDetails.getEmail()).get());

		if (publishType.equals("0")) {
			post.setIsPublished(false);
			post.setUpdatedAt(new Date());
		} else {
			post.setIsPublished(true);
			post.setPublishedAt(new Date());
		}

		postService.savePosts(post);
		postService.createExcerpt(post.getId());
		
		return "redirect:/";
	}

	@PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
	@GetMapping("/newpost")
	public String createPost(Model model, Post post, @AuthenticationPrincipal MyUserDetails user) {

		post.setAuthor(user.getUsername());
//		post.setUser(user);
		model.addAttribute("post", post);
		
		return "newPost";
	}


	
	@GetMapping("/showPost/{id}")
	public String showPost(@PathVariable(value="id") Integer id, Model model) {
		Optional<Post> optional = postService.getPostById(id);
		
		if(optional.isPresent()) {
			model.addAttribute("post", optional.get());
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		
		model.addAttribute("comments", new Comment());
		
		return "showPost";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
	@GetMapping("/updatePost/{id}")
	public String updatePost(@PathVariable(value="id") Integer id, Model model, Post post) {
		Optional<Post> optional =  postService.getPostById(id);
		StringBuilder tagName = new StringBuilder();
		
		if(optional.isPresent()) {
			post = optional.get();
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		
		for (Tag tag : post.getTag()) {
			tagName.append(tag.getName()).append(" ");
		}
		
		System.out.println(tagName);
		
		model.addAttribute("post", post);
		model.addAttribute("tagName", tagName.toString());
		
		return "newPost";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable(value="id") Integer id) {
		postService.deletePostsById(id);	

		return "redirect:/";
	}
	
}
