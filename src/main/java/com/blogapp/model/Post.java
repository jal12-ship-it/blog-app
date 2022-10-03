package com.blogapp.model;

import java.util.*;

import javax.persistence.*;

@Entity
public class Post  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String excerpt;
	@Column(length=1000)
	private String content;
	private String author;
	private Date publishedAt;
	@Column(columnDefinition = "boolean default false")
	private Boolean isPublished;
	private Date createdAt = new Date();
	private Date updatedAt = new Date();
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Tag> tag = new HashSet<>();
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
	private Set<Comment> comment = new HashSet<>();
	@ManyToOne
	private User user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Set<Tag> getTag() {
		return tag;
	}
	public void setTag(Set<Tag> tag) {
		this.tag = tag;
	}
	public Set<Comment> getComment() {
		return comment;
	}
	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}