package com.blogapp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapp.model.Post;
import com.blogapp.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public void savePosts(Post post) {
		postRepository.save(post);
	}

	public Set<Post> getPosts(Boolean isPublished) {
		return postRepository.findByIsPublished(isPublished);
	}

	public Optional<Post> getPostById(Integer id) {
		return postRepository.findById(id);
	}

	public void deletePostsById(Integer id) {
		postRepository.deleteById(id);
	}

	public Set<String> getAuthorList(Boolean isPublished) {
		return postRepository.findAuthorByIsPublished(isPublished);
	}

	public Set<Post> getPostsByAuthorAndTag(List<String> authorName, List<String> tagName, Boolean isPublished) {
		return postRepository.findByFilters(authorName, tagName, isPublished);
	}

	public Set<Post> getPostsByAuthorAndTagAndDate(List<String> authorName, List<String> tagName, Date startDate,
												   Date endDate, Boolean isPublished) {
		return postRepository.findByFilter(authorName, tagName, startDate, endDate, isPublished);
	}

	public Set<Post> search(String search, Boolean isPublished){
		return postRepository.findByKeyword(search, isPublished);
	}

	public void createExcerpt(Integer id) {
		postRepository.createExcerpt(id);
	}

}
