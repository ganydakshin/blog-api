package com.blog.api.ServiceTests;



import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.MediaType;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.MediaType;

import com.blog.api.domain.Post;
import com.blog.api.service.IdGenerator;
import com.blog.api.service.PostService;
import com.blog.api.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

import com.blog.api.domain.Comment;
import com.blog.api.domain.Post;
import com.blog.api.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Test
	public void checkUserDelete() {
		HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setFirstName("Mahendra");
		user.setLastName("Dhoni");
		user.setUsername("msd");
		
		try {
			
			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/users")
					 .header("accept", MediaType.APPLICATION_JSON_VALUE)
					 .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					 .body(body)
					 .asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			user.setId(responseBody.getLong("id"));
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(user));
			
			String link = "http://localhost:8080/blog/api/users/"+user.getId();
			jsonResponse = Unirest.delete(link)
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body1)
					.asJson();
			JSONObject responseBody2 = jsonResponse.getBody().getObject();
			//assertNull(jsonResponse.getBody());
			JSONObject del = new JSONObject("[{}]");
			assertEquals(jsonResponse.getBody(),del);
			//assertNotNull(jsonResponse.getBody());
			//assertEquals(responseBody.getString("firstName"), user.getFirstName());
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}	
private PostService postService;
private UserService userService;
	
	@Test
	/*
	 * Using Dummy
	 */
	public void addTest()
	{
		postService=new PostService();
		postService.init();
		postService.idGenerator=new IdGenerator();
		postService.idGenerator.init();
		List<Post> posts=postService.getAllPosts();
		int initialSize=posts.size();
		Post newPost=new Post();
		postService.add(newPost);
		posts=postService.getAllPosts();
		int finalSize=posts.size();
		assertEquals(initialSize,finalSize-1);
	}
	
	@Test (expectedExceptions=java.util.NoSuchElementException.class)
	/*
	 * Testing for Null
	 */
	public void getPostTest()
	{
		postService=new PostService();
		postService.init();
		Post gotPost=postService.getPost((long)1);
	}
	
	@Test
	/*
	 * Mock Test
	 */
	public void getPostTest2()
	{
		Post mockPost=mock(Post.class);
		Comment mockComment=mock(Comment.class);
		when(mockComment.getId()).thenReturn(123L);
		when(mockComment.getText()).thenReturn("Mock Comment");
		List<Comment> mockCommentList=new ArrayList<Comment>();
		mockCommentList.add(mockComment);
		when(mockPost.getContent()).thenReturn("Mock Post");
		when(mockPost.getType()).thenReturn("Mock Posttype");
		when(mockPost.getComments()).thenReturn(mockCommentList);
		postService=new PostService();
		postService.init();
		postService.idGenerator=new IdGenerator();
		postService.idGenerator.init();
		long id=postService.add(mockPost).getId();
		assertEquals(postService.getPost(id).getComments().get(0).getText(),"Mock Comment");
		assertEquals(postService.getPost(id).getType(),"Mock Posttype");
		
		assertEquals(postService.getPost(id).getComments().size(),1);
		assertEquals(postService.getPost(id).getContent(),"Mock Post");
	}
	
	@Test
	/*
	 * 
	 */
	public void updatePostTest()
	{
		Post mockPost=mock(Post.class);
		when(mockPost.getContent()).thenReturn("Mock Post");
		postService=new PostService();
		postService.init();
		postService.idGenerator=new IdGenerator();
		postService.idGenerator.init();
		long id=postService.add(mockPost).getId();
		String string=postService.getPost(id).getContent();
		when(mockPost.getContent()).thenReturn("This is Updated Mock Content");
		assertNotEquals(postService.update(mockPost).getContent(),string);
		assertEquals(postService.update(mockPost).getContent(),"This is Updated Mock Content");
	}
	

	@Test
	/*
	 * 
	 */
	public void  deletePostTest()
	{
		Post mockPost=mock(Post.class);
		when(mockPost.getContent()).thenReturn("Mock Post");
		postService=new PostService();
		postService.init();
		postService.idGenerator=new IdGenerator();
		postService.idGenerator.init();
		long id=postService.add(mockPost).getId();
		int size=postService.getAllPosts().size();
		postService.delete(id);
		assertEquals(size,postService.getAllPosts().size()+1);
	}
	
	
}
