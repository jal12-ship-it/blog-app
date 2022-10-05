package com.blogapp.repository;

import com.blogapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("select distinct t.name from Tag t left join t.post post " +
            "where post.isPublished = :isPublished")
    Set<String> findTagsByIsPublished(Boolean isPublished);

    @Query("select name from Tag")
    List<String> findName();

    Tag findByName(String eachTag);
}
