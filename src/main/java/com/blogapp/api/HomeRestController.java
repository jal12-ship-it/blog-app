package com.blogapp.api;

import com.blogapp.model.Filter;
import com.blogapp.model.Post;
import com.blogapp.service.PostService;
import com.blogapp.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeRestController {

    private final PostService postService;
    private final TagService tagService;


    public HomeRestController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping
    public Page<Post> findByFilter(
            Filter filter, String search, String sortField, String order,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        Boolean isPublished = true;
        String[] authorList = postService.getAuthorList(isPublished).toArray(String[]::new);
        String[] tagList = tagService.getTagsByIsPublished(isPublished).toArray(String[]::new);
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
        return pages;
    }
}

