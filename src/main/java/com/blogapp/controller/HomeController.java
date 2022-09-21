package com.blogapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@GetMapping
	public String home(Model model, String search) {
		
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

	@GetMapping("/filter/{filterBy}")
	public String filterBy(Model model, String filterType) {
		return null;
	}
	
	@GetMapping("/sort/{field}")
	public String getAllPostsWithSort(@PathVariable String field, Model model) {
		List<Posts> sortedList = homeService.findPostsWithSorting(field);
		model.addAttribute("postList", sortedList);
		
		return "home2";
	}
	
	@GetMapping("/page/{offset}")
	public String getAllPostsWithPageination(@PathVariable int offset, Model model) {
		Page<Posts> page = homeService.findPostsWithPagination(offset);
		model.addAttribute("postList", page);
		return "home2";
		
	}
	
	@PostMapping("/savePost")
	public String savePost(@ModelAttribute("post") Posts post, @ModelAttribute("tag") String tagString) {
		
		System.out.println(post.getTitle());
		List<String> tagList = new ArrayList<>(Arrays.asList(tagString.split(" ")));
		List<Tags> tagObjectList = new ArrayList<>();
		
		for (String eachTag : tagList) {
			Tags tag = new Tags();
			tag.setName(eachTag);
			tagObjectList.add(tag);
		}
		
		post.setTags(tagObjectList);
		post.setIsPublished(true);
		
		homeService.savePosts(post);
		homeService.createExcerpt(post.getId());
		
		return "redirect:/";
	}
	
	@GetMapping("/newpost")
	public String createPost(Model model, Posts post, String tag) {
		
		model.addAttribute("post", post);
		model.addAttribute("tag", tag);
		
		return "newPost";
	}
	
	@GetMapping("/showPost/{id}")
	public String showPost(@PathVariable(value="id") Integer id, Model model, Comments comments, Posts post) {
		Optional<Posts> optional = homeService.getPostById(id);
		
		if(optional.isPresent()) {
			post = optional.get();
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		
		System.out.println(post.getId());
		
		model.addAttribute("comments", comments);
		model.addAttribute("post", post);
		
		return "showPost";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable(value="id") Integer id) {
		
		homeService.deletePostsById(id);	
		return "redirect:/";
	}
	
	@PostMapping("/saveComment")
	public String saveComment(@ModelAttribute("comment") Comments comments, @ModelAttribute("postId") Integer id, Posts post) {
//		String com = comment.getComment();
		Optional<Posts> optional = homeService.getPostById(id);
		if(optional.isPresent()) {
			post = optional.get();
		}
		else {
			throw new RuntimeException("Post not found with id::" + id);
		}
		comments.setPost(post);
		post.getComments().add(comments);
		homeService.saveComments(comments);

		
		return "redirect:/showPost/" + post.getId();
	}
	

}
