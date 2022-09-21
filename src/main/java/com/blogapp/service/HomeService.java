package com.blogapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.model.Comments;
import com.blogapp.model.Posts;
import com.blogapp.model.Tags;
import com.blogapp.repository.CommentsRepository;
import com.blogapp.repository.PostsRepository;
import com.blogapp.repository.TagsRepository;

@Service
public class HomeService {

	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
	private TagsRepository tagsRepository;

	@Autowired
	private CommentsRepository commentsRepository;
	
	public List<Tags> getAllTags() {
		return tagsRepository.findAll();
	}
	
	public List<Posts> getAllPosts() {
		return postsRepository.findAll();
	}
	
	public Optional<Posts> getPostById(Integer id) {
		return postsRepository.findById(id);
	}
	
	public List<Posts> getByKeyword(String keyword){
		return postsRepository.findByKeyword(keyword);
	}
	
	public void createExcerpt(Integer id) {
		postsRepository.createExcerpt(id);
	}

	public void savePosts(Posts post) {
		postsRepository.save(post);
	}
	
	public void saveTags(Tags tag) {
		tagsRepository.save(tag);
	}
	
	public void saveComments(Comments comment) {
		commentsRepository.save(comment);
	}
	
	public void deletePostsById(Integer id) {
		postsRepository.deleteById(id);
	}

	public List<Comments> getAllComments() {
		return commentsRepository.findAll();
	}

	public List<Posts> findPostsWithSorting(String field) {
		return postsRepository.findAll(Sort.by(Sort.Direction.ASC, field));
	}
	
	public Page<Posts> findPostsWithPagination(int offset) {
		return postsRepository.findAll(PageRequest.of(offset, 10));
	}
}
