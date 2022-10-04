package com.blogapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	@Column(length=5000)
	private String message;
	@ManyToOne(fetch = FetchType.EAGER)
	private Post post;
	private Date createdAt = new Date();
	private Date updatedAt = new Date();

}