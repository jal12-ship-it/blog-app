package com.blogapp.repository;

import com.blogapp.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Post set excerpt = substring(content, 1, 100) where id = :id")
    void createExcerpt(@Param("id") Integer id);

    @Query("select distinct p from Post p where p.isPublished = ?1")
    Page<Post> findDistinctByIsPublished(Boolean isPublished, Pageable pageable);

    @Query("select distinct p from Post p where p.isPublished = ?1")
    List<Post> findDistinctByIsPublishedList(Boolean isPublished, Pageable pageable);

    @Query("""
            select distinct p from Post p left join p.tag tag
            where (upper(p.title) like upper(concat('%', ?1, '%'))
            or upper(p.content) like upper(concat('%', ?1, '%'))
            or upper(p.author) like upper(concat('%', ?1, '%'))
            or upper(tag.name) like upper(concat('%', ?1, '%')))
            and p.isPublished = ?2""")
    Page<Post> findByKeyword(String search, Boolean isPublished, Pageable pageable);

    @Query("select distinct author from Post where isPublished = :isPublished")
    Set<String> findDistinctAuthor(Boolean isPublished);

    @Query("""
            select distinct p from Post p inner join p.tag tag
            where p.author in ?1 and tag.name in ?2 and p.publishedAt between ?3 and ?4""")
    Page<Post> findByFilters(List<String> authors, List<String> names, LocalDate publishedAtStart,
                             LocalDate publishedAtEnd, Pageable pageable);


    Page<Post> findByUser_UsernameAndIsPublished(String username, Boolean isPublished, Pageable pageable);



}