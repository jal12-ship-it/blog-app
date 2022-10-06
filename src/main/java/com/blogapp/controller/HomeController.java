package com.blogapp.controller;

import com.blogapp.model.DateRange;
import com.blogapp.model.MyUserDetails;
import com.blogapp.model.Post;
import com.blogapp.service.PostService;
import com.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;

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

        DateRange dateRange = new DateRange();
        dateRange.setDateFrom(new Date());
        dateRange.setDateTo(new Date());

        model.addAttribute("filter", dateRange);
        model.addAttribute("authorList", authorList);
        model.addAttribute("tagList", tagList);
    }

    @GetMapping
    public String home(Model model, String search, String[] authorId, @ModelAttribute("authorList") String[] authorList,
                       String[] tagId, String sortField, @ModelAttribute("tagList") String[] tagList, String order,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {

        Boolean isPublished = true;
        Page<Post> pages;
        Pageable pageable = PageRequest.of(page, pageSize);

        if (search != null) {
            pages = postService.search(pageable, search, isPublished);
        } else if (authorId != null || tagId != null) {
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

            pages = postService.getPostsByAuthorAndTag(authorName, tagName, pageable);
        } else {
            if (sortField != null) {
                if (order.equals("asc")) {
                    pageable = PageRequest.of(page, pageSize, Sort.by(sortField).ascending());
                } else {
                    pageable = PageRequest.of(page, pageSize, Sort.by(sortField).descending());
                }

            }
            pages = postService.getPosts(isPublished, pageable);
        }

        model.addAttribute("postList", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("search", search);
        model.addAttribute("order", order);
        model.addAttribute("totalPage", pages.getTotalPages());

        return "home2";
    }

    @GetMapping("/drafts")
    public String getDraftsPage(Model model, @RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize, @AuthenticationPrincipal MyUserDetails user) {

        Boolean isPublished = false;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Post> pages = postService.getPostsByUser(isPublished, pageable, user.getUsername());

        model.addAttribute("postList", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPage", pages.getTotalPages());
        return "drafts";
    }
}
