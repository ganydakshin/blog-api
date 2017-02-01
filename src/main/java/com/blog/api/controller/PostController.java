package com.blog.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.domain.Post;
import com.blog.api.service.PostService;

@RestController
@RequestMapping(path="/posts", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
public class PostController {
	
	@Autowired
	PostService postService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Post> list(@RequestParam Map<String,String> params) {
		List<Post> posts = postService.search(params);
		return posts;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Post save(@Validated @RequestBody Post post) {
		return postService.add(post);
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public Post get(@PathVariable Long id) {
		Post post = postService.getPost(id);
		return post;
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.PUT) 
	public Post update(@Validated @RequestBody Post post) {
		return postService.update(post);
	}
	
	@RequestMapping(path="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		postService.delete(id);
	}
}
