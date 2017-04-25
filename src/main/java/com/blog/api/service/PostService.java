package com.blog.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.domain.Post;

@Service
public class PostService {
	
	List<Post> posts;
	
	@Autowired
	public
	IdGenerator idGenerator;
	
	@PostConstruct
	public void init() {
		posts = new ArrayList<>();
	}
	
	public List<Post> getAllPosts() {
		return posts;
	}
	
	public Post getPost(Long id) {
		Post post = posts.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst().get();
		return post;
	}
	
	public Post add(Post newPost) {
		newPost.setId(idGenerator.generate());
		posts.add(newPost);
		return newPost;
	}
	
	public Post update(Post updatedPost) {
		Post post = getPost(updatedPost.getId());
		post.setContent(updatedPost.getContent());
		return post;
	}
	
	public void delete(Long id) {
		Post post = getPost(id);
		posts.remove(post);
	}

}
