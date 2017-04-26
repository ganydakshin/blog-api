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
}
