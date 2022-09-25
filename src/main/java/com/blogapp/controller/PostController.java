package com.blogapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import com.blogapp.model.Tags;
import com.blogapp.service.HomeService;
import com.blogapp.service.PostService;

@Controller
public class PostController {

	@Autowired
	private HomeService homeService;
	@Autowired
	private PostService postService;
	
	@PostMapping("/savePost")
	public String savePost(@ModelAttribute("post") Posts post, String tagString, Boolean publishType) {
		
		List<String> tagList = new ArrayList<>(Arrays.asList(tagString.split(" ")));
		
		for (String eachTag : tagList) {
			Tags tag = new Tags();
			if(postService.getAllTags().contains(eachTag)) {
//				tag = postService.getTagByName(eachTag);
				post.getTags().add(tag);
			} else {	
			tag.setName(eachTag);
			post.getTags().add(tag);
			}
		}
		
		post.setIsPublished(publishType);
		post.setPublishedAt(new Date());
		post.setUpdatedAt(new Date());
		
		postService.savePosts(post);
		homeService.createExcerpt(post.getId());
		
		return "redirect:/";
	}
	

	@GetMapping("/newpost")
	public String createPost(Model model, Posts post) {
		model.addAttribute("post", post);
		return "newPost";
	}
	
	@GetMapping("/showPost/{id}")
	public String showPost(@PathVariable(value="id") Integer id, Model model, Comments comments, Posts post) {
		Optional<Posts> optional = postService.getPostById(id);
		
		if(optional.isPresent()) {
			post = optional.get();
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		
		model.addAttribute("comments", comments);
		model.addAttribute("post", post);
		
		return "showPost";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable(value="id") Integer id) {
		
		postService.deletePostsById(id);	
		return "redirect:/";
	}
	
}
