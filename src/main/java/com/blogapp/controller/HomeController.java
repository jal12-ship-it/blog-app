package com.blogapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogapp.model.Posts;
import com.blogapp.service.HomeService;
import com.blogapp.service.PostService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private static final boolean String = false;
	@Autowired
	private HomeService homeService;
	@Autowired
	private PostService postService;
	
	@ModelAttribute
	public void addFilterLists(Model model) {
		List<String> authorList = postService.getAuthorList();
		List<String> tagList = postService.getAllTags();
		
		model.addAttribute("authorList", authorList);
		model.addAttribute("tagList", tagList);
	}
	
	@GetMapping
	public String home(Model model, String search, String[] authorId, @ModelAttribute("authorList") String[] authorList, @ModelAttribute("tagList") String[] tagList,
									String[] tagId, String sortField, @RequestParam(defaultValue= "1") Integer start,
																	  @RequestParam(defaultValue= "10") Integer limit,
												   					  @RequestParam(defaultValue = "asc") String order) {
		
		List<Posts> list = null;
		Page<Posts> page = null;
		List<Integer> PostIds = null;
		Boolean isPublished = true;
		Pageable pageable = PageRequest.of(start/limit, limit);
		
		if(search!=null) {
			page = homeService.getByKeyword(pageable, search);
		}
		else if(authorId != null || tagId != null) {
			List<String> authorName = new ArrayList<>();
			List<String> tagName = new ArrayList<>();
			for (String id : authorId) {
				String name = authorList[Integer.parseInt(id)];
				authorName.add(name);
			}			
			for (String id : tagId) {
				String name = tagList[Integer.parseInt(id)];
				tagName.add(name);
		}
		
			page = postService.getAllPostsByAuthor(authorName, tagName, pageable);
			
//			for (String id : authorId) {
//				String name = authorList[Integer.parseInt(id)];
//				page.and(postService.getPostsByAuthor(name, pageable));
//			}
//		

		}
		else if(sortField != null) {
			if(order.equals("asc")) {
				pageable = PageRequest.of(start/limit, limit, Sort.by(sortField).ascending());
			} else {
				pageable = PageRequest.of(start/limit, limit, Sort.by(sortField).descending());
			}
			page = postService.getAllPosts(pageable);
		}
		else {
			page = postService.getAllPosts(pageable);
		}
		
		model.addAttribute("postList", page);
		model.addAttribute("currentPage", start/limit);
		model.addAttribute("limit", limit);
		model.addAttribute("search", search);

		
		
		return "home2";
	}

	
}
