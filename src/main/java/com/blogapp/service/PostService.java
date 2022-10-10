package com.blogapp.service;

import com.blogapp.model.MyUserDetails;
import com.blogapp.model.Post;
import com.blogapp.repository.PostRepository;
import com.blogapp.repository.TagRepository;
import com.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;

    public void savePosts(Post post) {
        postRepository.save(post);
    }

    public Page<Post> getPosts(Boolean isPublished, Pageable pageable) {
        return postRepository.findDistinctByIsPublished(isPublished, pageable);
    }

    public Optional<Post> getPostById(Integer id) {
        return postRepository.findById(id);
    }

    public void deletePostsById(Integer id) {
        postRepository.deleteById(id);
    }

    public Set<String> getAuthorList(Boolean isPublished) {
        return postRepository.findDistinctAuthor(isPublished);
    }

    public Page<Post> getPostByFilters(List<String> authorName, List<String> tagName,
                                       LocalDate publishedAtStart, LocalDate publishedAtEnd, Pageable pageable) {
        return postRepository.findByFilters(authorName, tagName, publishedAtStart, publishedAtEnd, pageable);
    }

    public Page<Post> search(Pageable pageable, String search, Boolean isPublished) {
        return postRepository.findByKeyword(search, isPublished, pageable);
    }

    public void createExcerpt(Integer id) {
        postRepository.createExcerpt(id);
    }

    public Page<Post> getPostsByUser(Boolean isPublished, Pageable pageable, String username) {
        return postRepository.findByUser_UsernameAndIsPublished(username, isPublished, pageable);
    }

    public void updatePostDate(Post post, String publishType) {
        switch (publishType) {
            case "0" -> {
                post.setIsPublished(false);
                post.setUpdatedAt(new Date());
            }
            case "1" -> {
                post.setIsPublished(true);
                post.setPublishedAt(LocalDate.now());
            }
        }
    }

    public void saveUser(Post post, MyUserDetails userDetails) {
        post.setUser(userRepository.findByEmail(userDetails.getEmail()).get());
    }

    public boolean isAuthorized(Integer id, MyUserDetails user) {
        return Objects.equals(user.getUsername(), getPostById(id).get().getUser().getUsername());
    }

}
