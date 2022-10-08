package com.blogapp.controller;

import com.blogapp.model.Filter;
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

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
        Set<String> tagList = tagService.getTagsByIsPublished(isPublished);

        model.addAttribute("authorList", authorList);
        model.addAttribute("tagList", tagList);
    }

    @GetMapping
    public String home(Model model, String search, String sortField, String order,
                       @ModelAttribute("filter") Filter filter,
                       @ModelAttribute("authorList") String[] authorList,
                       @ModelAttribute("tagList") String[] tagList,
                       @RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) throws ParseException {

        Boolean isPublished = true;
        Page<Post> pages;
        Pageable pageable = PageRequest.of(page, pageSize);

        if (search != null) {
            pages = postService.search(pageable, search, isPublished);
        } else if (filter.isValid()) {
            pages = postService.getPostByFilters(
                    filter.getNameList(filter.getAuthorId(), authorList),
                    filter.getNameList(filter.getTagId(), tagList),
                    filter.getLocalDateFrom(filter.getDateFrom()),
                    filter.getLocalDateTo(filter.getDateTo()),
                    pageable
                    );
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

        model.addAttribute("filter", filter);
        model.addAttribute("postList", pages);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("search", search);
        model.addAttribute("order", order);
        model.addAttribute("totalPage", pages.getTotalPages());

        return "home";
    }

    @GetMapping("/drafts")
    public String getDraftsPage(Model model, @AuthenticationPrincipal MyUserDetails user,
                                @RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {

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
