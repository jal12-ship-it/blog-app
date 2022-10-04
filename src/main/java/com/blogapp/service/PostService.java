package com.blogapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapp.model.Post;
import com.blogapp.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public void savePosts(Post post) {
		postRepository.save(post);
	}

//	public Page<Post> getPost(Boolean isPublished, Pageable pageable) {
//		return postRepository.findByIsPublishedPageable(isPublished, pageable);
//	}

	public Page<Post> getPosts(Boolean isPublished, Pageable pageable) {
		return postRepository.findByIsPublished(isPublished, pageable);
	}

	public Optional<Post> getPostById(Integer id) {
		return postRepository.findById(id);
	}

	public void deletePostsById(Integer id) {
		postRepository.deleteById(id);
	}

	public Set<String> getAuthorList(Boolean isPublished) {
		return postRepository.findDistinctAuthor(isPublished);
	}

	public Page<Post> getPostsByAuthorAndTag(List<String> authorName, List<String> tagName, Pageable pageable) {
		return postRepository.findDistinctByAuthorInAndTag_NameIn(authorName, tagName, pageable);
	}

	public Page<Post> search(Pageable pageable, String search, Boolean isPublished){
		return postRepository.findByKeyword(search, pageable, isPublished);
	}

	public void createExcerpt(Integer id) {
		postRepository.createExcerpt(id);
	}

	public Page<Post> getPostsByUser(Boolean isPublished, Pageable pageable, String username) {
		return postRepository.findByUser_UsernameAndIsPublished(username, isPublished, pageable);
	}

}
