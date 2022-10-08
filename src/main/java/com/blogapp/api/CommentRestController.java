package com.blogapp.api;

import com.blogapp.model.Comment;
import com.blogapp.model.MyUserDetails;
import com.blogapp.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService1) {
        this.commentService = commentService1;
    }

    @GetMapping
    public Page<Comment> findAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentService.getComments(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Comment> findById(@PathVariable Integer id) {
        return commentService.getCommentById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody Comment comment, HttpServletResponse httpResponse,
                       @AuthenticationPrincipal MyUserDetails user) {
        commentService.saveComment(comment);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public void update(@RequestBody Comment comment, @PathVariable Integer id,
                       @AuthenticationPrincipal MyUserDetails user) {
        if(!commentService.isAuthorized(id, user))
            throw new RuntimeException("Unauthorized access");

        comment.setId(id);
        commentService.saveComment(comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public void delete(@PathVariable Integer id, @AuthenticationPrincipal MyUserDetails user) {
        if(!commentService.isAuthorized(id, user))
            throw new RuntimeException("Unauthorized access");

        commentService.deleteCommentById(id);
    }
}
