package com.blogapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapp.model.Post;
import com.blogapp.model.Tag;
import com.blogapp.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	private TagRepository tagRepository;
	
	public List<String> getTags() {
		return tagRepository.findAllTags();
	}

	public List<Post> getPostByTag(String name, Pageable pageable) {
		return tagRepository.findByName(name, pageable);
	}
	
	public void saveTags(Tag tag) {
		tagRepository.save(tag);
	}
}
