package com.blogapp.service;

import com.blogapp.model.Tag;
import com.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public Tag getTagByName(String eachTag) {
        return tagRepository.findByName(eachTag);
    }
}
