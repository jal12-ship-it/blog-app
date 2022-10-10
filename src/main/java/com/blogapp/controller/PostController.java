package com.blogapp.controller;

import com.blogapp.model.Comment;
import com.blogapp.model.MyUserDetails;
import com.blogapp.model.Post;
import com.blogapp.service.PostService;
import com.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private TagService tagService;
    @Autowired
    private PostService postService;


    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post, String tagString, String publishType,
                           @AuthenticationPrincipal MyUserDetails userDetails) {
        tagService.saveTags(post, tagString);
        postService.updatePostDate(post, publishType);
        postService.saveUser(post, userDetails);

        postService.savePosts(post);
        postService.createExcerpt(post.getId());

        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    @GetMapping("/newpost")
    public String createPost(Model model, Post post, @AuthenticationPrincipal MyUserDetails user) {

        post.setAuthor(user.getUsername());
        model.addAttribute("post", post);

        return "newPost";
    }


    @GetMapping("/showPost/{id}")
    public String showPost(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Post> optional = postService.getPostById(id);

        if (optional.isPresent()) {
            model.addAttribute("post", optional.get());
        } else {
            throw new RuntimeException("Post not found with id::" + id);
        }

        model.addAttribute("comments", new Comment());

        return "showPost";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    @GetMapping("/updatePost/{id}")
    public String updatePost(@PathVariable(value = "id") Integer id, Model model, @AuthenticationPrincipal MyUserDetails user) {
        Optional<Post> optional = postService.getPostById(id);

        if (optional.isEmpty())
            throw new RuntimeException("Post not found with id::" + id);
        else if(!postService.isAuthorized(id, user))
            throw new RuntimeException("Unauthorized Access");

        model.addAttribute("post", optional.get());
        model.addAttribute("tagString", tagService.getTagString(optional.get()));

        return "newPost";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable(value = "id") Integer id, @AuthenticationPrincipal MyUserDetails user) {
        if(!postService.isAuthorized(id, user))
            throw new RuntimeException("Unauthorized Access");

        postService.deletePostsById(id);

        return "redirect:/";
    }

}
