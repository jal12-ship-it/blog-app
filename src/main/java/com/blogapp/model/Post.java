package com.blogapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String excerpt;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String author;
    private LocalDate publishedAt;
    @Column(columnDefinition = "boolean default false")
    private Boolean isPublished;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Tag> tag = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private Set<Comment> comment = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Users user;
}