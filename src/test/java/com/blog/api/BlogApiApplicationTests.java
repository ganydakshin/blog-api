package com.blog.api;

import static org.testng.Assert.*;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.testng.annotations.Test;

import com.blog.api.domain.Comment;
import com.blog.api.domain.Post;
import com.blog.api.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class BlogApiApplicationTests {

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
			//post.setContent("I am 100% fine");
			Comment comment = new Comment();
			comment.setPostId(post.getId());
			comment.setText("You are really mad");
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
}
