package com.blogapp.service;

import com.blogapp.model.Comment;
import com.blogapp.model.Post;
import com.blogapp.model.Tag;
import com.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Set<String> getTagsByIsPublished(Boolean isPublished) {
        return tagRepository.findTagsByIsPublished(isPublished);
    }

    public Page<Tag> getTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    public Tag getTagByName(String eachTag) {
        return tagRepository.findByName(eachTag);
    }

    public void saveTags(Post post, String tagString) {
        Set<String> tagList = new HashSet<>(Arrays.asList(tagString.split(" ")));
        for (String eachTag : tagList) {
            Tag tag = getTagByName(eachTag) == null ? new Tag() : getTagByName(eachTag);

            tag.setName(eachTag);
            post.getTag().add(tag);
//            tag.getPost().add(post);
            }
        }

    public String getTagString(Post post) {
        StringBuilder tagString = new StringBuilder();

        for (Tag tag : post.getTag()) {
            tagString.append(tag.getName()).append(" ");
        }

        return tagString.toString();
    }

    public Optional<Tag> getTagById(Integer id) {
        return tagRepository.findById(id);
    }

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void deleteTagById(Integer id) {
        tagRepository.deleteById(id);
    }
}
