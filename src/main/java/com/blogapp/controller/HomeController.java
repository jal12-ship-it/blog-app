package com.blogapp.controller;

import java.util.List;

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
	public String home2(Model model, String keyword) {
		
		List<Tags> tags = homeService.getAllTags();
		
		if(keyword!=null) {
			List<Posts> list = homeService.getByKeyword(keyword);
			model.addAttribute("postList", list);
		}else {
			List<Posts> list = homeService.getAllPosts();
			model.addAttribute("postList", list);}

		model.addAttribute("tags", tags);
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
	public String savePost(@ModelAttribute("post") Posts post, @ModelAttribute("tag") Tags tag) {
		homeService.savePosts(post);
		homeService.saveTags(tag);
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable(value="id") Integer id) {
		homeService.deletePostsById(id);	
		return "redirect:/";
	}
}
