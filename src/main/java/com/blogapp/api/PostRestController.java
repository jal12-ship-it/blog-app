package com.blogapp.api;

import com.blogapp.model.Comment;
import com.blogapp.model.MyUserDetails;
import com.blogapp.model.Post;
import com.blogapp.service.CommentService;
import com.blogapp.service.PostService;
import com.blogapp.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;
    private final TagService tagService;

    public PostRestController(PostService postService, TagService tagService, CommentService commentService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping
    public Page<Post> findByIsPublished() {
        Pageable postPage = PageRequest.of(0, 10);
        return postService.getPosts(true, postPage);
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Integer id) {
        return postService.getPostById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    public void create(@RequestBody Post post,String tagString, String publishType,
                       @AuthenticationPrincipal MyUserDetails userDetails) {
        tagService.saveTags(post, tagString);
        postService.updatePostDate(post, publishType);
        postService.saveUser(post, userDetails);

        postService.savePosts(post);
        postService.createExcerpt(post.getId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    public void update(@RequestBody Post post, @PathVariable Integer id, @AuthenticationPrincipal MyUserDetails user) {
        if(!postService.isAuthorized(id, user))
            throw new RuntimeException("This operation cannot be performed by you");

        post.setId(id);
        for ( Comment comment : post.getComment() ) {
            comment.setPost(post);
        }

        postService.savePosts(post);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('AUTHOR', 'ADMIN')")
    public void delete(@PathVariable Integer id, @AuthenticationPrincipal MyUserDetails user) {
        if(!postService.isAuthorized(id, user))
            throw new RuntimeException("This operation cannot be performed by you");

        postService.deletePostsById(id);
    }

}