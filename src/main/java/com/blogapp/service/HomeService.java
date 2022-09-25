package com.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapp.model.Posts;
import com.blogapp.model.Tags;
import com.blogapp.repository.PostsRepository;
import com.blogapp.repository.TagsRepository;

@Service
public class HomeService {

	@Autowired
	private PostsRepository postsRepository;

	@Autowired
	private TagsRepository tagsRepository;

	public List<Tags> getAllTags() {
		return tagsRepository.findAll();
	}

	public Page<Posts> getByKeyword(Pageable pageable, String keyword){
		return postsRepository.findByKeyword(pageable, keyword);
	}

	public void createExcerpt(Integer id) {
		postsRepository.createExcerpt(id);
	}

	public void saveTags(Tags tag) {
		tagsRepository.save(tag);
	}

}
