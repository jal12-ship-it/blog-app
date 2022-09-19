package com.blogapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.blogapp.model.Posts;
import com.blogapp.model.Tags;
import com.blogapp.service.HomeService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private HomeService homeService;

	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping
	public String home2(Model model, String search) {
		
		List<Tags> tags = homeService.getAllTags();
		
		if(search!=null) {
			List<Posts> list = homeService.getByKeyword(search);
			model.addAttribute("postList", list);
		}else {
			List<Posts> list = homeService.getAllPosts();
			model.addAttribute("postList", list);}

		model.addAttribute("tags", tags);
		model.addAttribute("search", search);
		return "home2";
	}

	
	@GetMapping("/edit")
	public String createPost(Model model) {
		Posts post = new Posts();
		Tags tag = new Tags();
		model.addAttribute("post", post);
		model.addAttribute("tag", tag);
		return "newPost";
	}

	@PostMapping("/newPost")
	public String savePost(@ModelAttribute("post") Posts post, @ModelAttribute("tag") String tag) {
		
		ArrayList<Tags> tags = new ArrayList<Tags>(Arrays.asList(tag.split(", "));
		
		for (Tags tag : tags) {
			post.getTags().add(tag);
		}
		homeService.savePosts(post);
		homeService.createExcerpt(post.getId());
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable(value="id") Integer id) {
		homeService.deletePostsById(id);	
		return "redirect:/";
	}
	
	@GetMapping("/showPost/{id}")
	public String showPost(@PathVariable(value="id") Integer id, Model model) {
		Optional<Posts> optional = homeService.getPostById(id);
		Posts post = null;
		if(optional.isPresent()) {
			post = optional.get();
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		
		model.addAttribute("post", post);
		
		return "showPost";
	}
}
