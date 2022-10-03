package com.blogapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogapp.model.Post;
import com.blogapp.service.PostService;
import com.blogapp.service.TagService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private TagService tagService;
	@Autowired
	private PostService postService;
	
	@ModelAttribute
	public void addFilterLists(Model model) {
		Boolean isPublished = true;
		Set<String> authorList = postService.getAuthorList(isPublished);
		Set<String> tagList = tagService.getTags(isPublished);
		
		model.addAttribute("authorList", authorList);
		model.addAttribute("tagList", tagList);
	}
	
	@GetMapping
	public String home(Model model, String search, String[] authorId, @ModelAttribute("authorList") String[] authorList,
									String[] tagId, String sortField, @ModelAttribute("tagList") String[] tagList,
																	  @RequestParam(defaultValue= "0") Integer page,
																	  @RequestParam(defaultValue= "10") Integer pageSize,
												   					  @RequestParam(defaultValue = "asc") String order) {
		
		Boolean isPublished = true;
		Set<Post> posts = postService.getPosts(isPublished);
		
		Pageable pageable = PageRequest.of(page, pageSize);

		if(search!=null) {
			posts.retainAll(postService.search(search, isPublished));
		}
		if(authorId != null || tagId != null) {
			List<String> authorName = authorId == null ? Arrays.asList(authorList) : new ArrayList<>();
			List<String> tagName = tagId == null ? Arrays.asList(tagList) : new ArrayList<>();

			if (authorId != null) {
				Arrays.stream(authorId)
						.map(id -> authorList[Integer.parseInt(id)])
						.forEach(authorName::add);
			}
			if (tagId != null) {
				Arrays.stream(tagId)
						.map(id -> tagList[Integer.parseInt(id)])
						.forEach(tagName::add);
			}

			posts.retainAll(postService.getPostsByAuthorAndTag(authorName, tagName, isPublished));
		}
		if(sortField != null) {
			if(order.equals("asc")) {
				pageable = PageRequest.of(page, pageSize, Sort.by(sortField).ascending());
			} else {
				pageable = PageRequest.of(page, pageSize, Sort.by(sortField).descending());
			}
		}

		Page<Post> pages = new PageImpl<>(new ArrayList<>(posts), pageable, posts.size());

		model.addAttribute("postList", pages);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("search", search);

		return "home2";
	}

	@GetMapping("/drafts")
	public String getDraftsPage(Model model, @RequestParam(defaultValue= "0") Integer page,
											 @RequestParam(defaultValue= "10") Integer pageSize	) {

		Boolean isPublished = false;
		Set<Post> posts = postService.getPosts(isPublished);

		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Post> pages = new PageImpl<>(new ArrayList<>(posts), pageable, posts.size());

		model.addAttribute("postList", pages);
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", pageSize);
		return "drafts";
	}

}
