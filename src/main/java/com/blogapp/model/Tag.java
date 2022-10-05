package com.blogapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();
    @ManyToMany(mappedBy = "tag", cascade = {CascadeType.PERSIST, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Post> post = new HashSet<>();


}
