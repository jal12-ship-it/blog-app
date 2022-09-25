package com.blogapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapp.model.Posts;
import com.blogapp.repository.PostsRepository;
import com.blogapp.repository.TagsRepository;

@Service
public class PostService {
	
	@Autowired
	private PostsRepository postsRepository;
	@Autowired
	private TagsRepository tagsRepository;

	public void savePosts(Posts post) {
		postsRepository.save(post);
	}

	public Page<Posts> getAllPosts(Example<Posts> isPublished, Pageable pageable) {
		return postsRepository.findAll(isPublished, pageable);
	}
	
	public Page<Posts> getAllPosts(Pageable pageable) {
		return postsRepository.findAll(pageable);
	}

	public Optional<Posts> getPostById(Integer id) {
		return postsRepository.findById(id);
	}

	public void deletePostsById(Integer id) {
		postsRepository.deleteById(id);
	}

	public List<String> getAuthorList() {
		return postsRepository.findDistictAuthor();
	}

	public List<String> getAllTags() {
		return tagsRepository.findAllTags();
	}

	public List<Posts> getPostByTag(String name, Pageable pageable) {
		return tagsRepository.findByName(name, pageable);
	}

	public Page<Posts> getAllPostsByAuthor(List<String> authorName, List<String> tagName, Pageable pageable) {
		return postsRepository.findByAuthor(authorName, tagName, pageable);
	}


}
