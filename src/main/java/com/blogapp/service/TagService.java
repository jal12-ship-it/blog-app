package com.blogapp.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.model.Tag;
import com.blogapp.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	private TagRepository tagRepository;
	
	public Set<String> getTags(Boolean isPublished) {
		return tagRepository.findTagsByIsPublished(isPublished);
	}

	public List<String> getTags() {
		return tagRepository.findName();
	}
	public void saveTags(Tag tag) {
		tagRepository.save(tag);
	}
}
