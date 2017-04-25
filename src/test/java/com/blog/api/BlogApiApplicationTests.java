package com.blog.api;



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
public class BlogApiApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void test_post_users_api() {
		HttpResponse<JsonNode> jsonResponse;
		
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setFirstName("Sarah");
		user.setLastName("Palin");
		user.setUsername("s.palin");
		
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(user));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/users")
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body)
					.asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody.getString("firstName"), user.getFirstName());
			
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPostsApi() {
		HttpResponse<JsonNode> jsonResponse;
		ObjectMapper mapper = new ObjectMapper();
		Post post = new Post();
		post.setUsername("Gany");
		post.setContent("Life is Miserable");
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/posts")
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body)
					.asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody.getString("username"), post.getUsername());
		}
		catch(UnirestException e) {
			e.printStackTrace();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		HttpResponse<JsonNode> jsonResponse;
		ObjectMapper mapper = new ObjectMapper();
		Post post = new Post();
		post.setUsername("Raja");
		post.setContent("I am mad");
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/posts")
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body)
					.asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			//assertNotNull(jsonResponse.getBody());
			//assertEquals(responseBody.getString("content"), post.getContent());
			System.out.println(responseBody.getLong("id"));
			post.setId(responseBody.getLong("id"));
			post.setContent("I am 100% fine");
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(post));
			String link = "http://localhost:8080/blog/api/posts/"+post.getId();
			jsonResponse = Unirest.put(link)
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body1)
					.asJson();
			JSONObject responseBody2 = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody2.getString("content"), post.getContent());
		}
		catch(UnirestException e) {
			e.printStackTrace();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testComments() {
		HttpResponse<JsonNode> jsonResponse;
		ObjectMapper mapper = new ObjectMapper();
		Post post = new Post();
		post.setUsername("Naveen");
		post.setContent("I am mad");
		post.setType("Commercial Post");
		Comment comment = new Comment();
		
		List<Comment> comments = new ArrayList<Comment>();
		comment.setId(134L);
		comment.setText("You are really mad");
		comments.add(comment);
		
		post.setComments(comments);
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/posts")
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body)
					.asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			//assertNotNull(jsonResponse.getBody());
			//assertEquals(responseBody.getString("content"), post.getContent());
			System.out.println(responseBody.getLong("id"));
			post.setId(responseBody.getLong("id"));
			//post.setContent("I am 100% fine");
			//List<Comment> comments = new ArrayList<Comment>();
			
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(comment));
			
			String link = "http://localhost:8080/blog/api/posts/"+post.getId()+"/comments";
			jsonResponse = Unirest.post(link)
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body1)
					.asJson();
			JSONObject responseBody2 = jsonResponse.getBody().getObject();
			assertNotNull(jsonResponse.getBody());
			assertEquals(responseBody2.getString("text"), comment.getText());
		}
		catch(UnirestException e) {
			e.printStackTrace();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() {
		HttpResponse<JsonNode> jsonResponse;
		ObjectMapper mapper = new ObjectMapper();
		Post post = new Post();
		post.setUsername("Venkat");
		post.setContent("I am a psychopath");
		try {
			JsonNode body = new JsonNode(mapper.writeValueAsString(post));
			jsonResponse = Unirest.post("http://localhost:8080/blog/api/posts")
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body)
					.asJson();
			JSONObject responseBody = jsonResponse.getBody().getObject();
			//assertNotNull(jsonResponse.getBody());
			//assertEquals(responseBody.getString("content"), post.getContent());
			System.out.println(responseBody.getLong("id"));
			post.setId(responseBody.getLong("id"));
			//post.setContent("I am 100% fine");
			
			JsonNode body1 = new JsonNode(mapper.writeValueAsString(post));
			
			String link = "http://localhost:8080/blog/api/posts/"+post.getId();
			jsonResponse = Unirest.delete(link)
					.header("accept", MediaType.APPLICATION_JSON_VALUE)
					.header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
					.body(body1)
					.asJson();
			JSONObject responseBody2 = jsonResponse.getBody().getObject();
			//assertNull(jsonResponse.getBody());
			JSONObject del = new JSONObject("[{}]");
			assertEquals(jsonResponse.getBody(),del);
			//assertEquals(responseBody2.getString("text"), comment.getText());
		}
		catch(UnirestException e) {
			e.printStackTrace();
		} catch(JsonProcessingException e) {
			e.printStackTrace();
		}
	}

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
	
	/*
	 **********************************
	 * 			USER TESTS			  *
	 **********************************
	 */
	@Test
	/*
	 * 
	 */
	public void listTest()
	{
		User mockUser=mock(User.class);
		when(mockUser.getFirstName()).thenReturn("Venkat");
		when(mockUser.getLastName()).thenReturn("is a lube shit");
		when(mockUser.getUsername()).thenReturn("naveenrk93");
		userService=new UserService();
		userService.init();
		userService.idGenerator=new IdGenerator();
		userService.idGenerator.init();
		assertEquals(0,userService.list().size());
		userService.save(mockUser);
		assertEquals(1,userService.list().size());
		
	}
	
	@Test
	/*
	 * 
	 */
	public void saveUserTest() 
	{
		User mockUser=mock(User.class);
		when(mockUser.getFirstName()).thenReturn("Naveen");
		when(mockUser.getLastName()).thenReturn("is soooo great!!!");
		when(mockUser.getUsername()).thenReturn("naveenrk93");
		userService=new UserService();
		userService.init();
		userService.idGenerator=new IdGenerator();
		userService.idGenerator.init();
		int initialSize=userService.list().size();
		userService.save(mockUser);
		int finalSize=userService.list().size();
		assertEquals(initialSize,finalSize-1);
		
	}
	
	@Test
	/*
	 * 
	 */
	public void listUserTest()
	{
		User mockUser=mock(User.class);
		when(mockUser.getFirstName()).thenReturn("Ganesh");
		when(mockUser.getLastName()).thenReturn("is an idiot");
		when(mockUser.getUsername()).thenReturn("naveenrk93");
		userService=new UserService();
		userService.init();
		userService.idGenerator=new IdGenerator();
		userService.idGenerator.init();
		userService.save(mockUser);
		for(User u:userService.list())
		{
			u.getFirstName();
		}
		verify(mockUser,times(1)).getFirstName();
		assertEquals(1,userService.list().size());
	}
	
	@Test
	/*
	 * 
	 */
	public void updateUserTest()
	{	
		User mockUser=mock(User.class);
		when(mockUser.getFirstName()).thenReturn("Venkat");
		when(mockUser.getLastName()).thenReturn("is a lube shit");
		when(mockUser.getUsername()).thenReturn("naveenrk93");
		userService=new UserService();
		userService.init();
		userService.idGenerator=new IdGenerator();
		userService.idGenerator.init();
		User user=userService.save(mockUser);
		String initialFirstName=userService.getUser(user.getId()).getFirstName();
		user.setFirstName("Changed");
		userService.update(user);
		String finalFirstName=userService.getUser(user.getId()).getFirstName();
		assertNotEquals(initialFirstName,finalFirstName);
		assertNotEquals(finalFirstName,"Venkat");
		assertEquals(finalFirstName,"Changed");
	}
	
	@Test
	/*
	 * 
	 */
	public void deleteTest()
	{
		User mockUser=mock(User.class);
		when(mockUser.getFirstName()).thenReturn("Venkat");
		when(mockUser.getLastName()).thenReturn("is a lube shit");
		when(mockUser.getUsername()).thenReturn("naveenrk93");
		userService=new UserService();
		userService.init();
		userService.idGenerator=new IdGenerator();
		userService.idGenerator.init();
		User user=userService.save(mockUser);
		int initialSize=userService.list().size();
		userService.delete(user.getId());
		int finalSize=userService.list().size();
		assertNotEquals(finalSize,initialSize);
		assertEquals(initialSize,1);
		assertEquals(finalSize,0);
	}
}
