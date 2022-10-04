package com.blogapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
