package com.blogapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
	private Date publishedAt = new Date();
	@Column(columnDefinition = "boolean default false")
	private Boolean isPublished;
	private Date createdAt = new Date();
	private Date updatedAt = new Date();
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Tag> tag = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
	private List<Comment> comment = new ArrayList<>();
	
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
	public List<Tag> getTag() {
		return tag;
	}
	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", excerpt=" + excerpt + ", content=" + content + ", author="
				+ author + ", publishedAt=" + publishedAt + ", isPublished=" + isPublished + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", tag=" + tag + ", comment=" + comment + "]";
	}
	
	
	
}