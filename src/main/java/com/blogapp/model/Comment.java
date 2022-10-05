package com.blogapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    private String name;
    private String email;
    @Column(length = 5000)
    private String message;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();

}