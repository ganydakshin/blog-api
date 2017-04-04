package com.blog.api;

import static org.testng.Assert.*;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.testng.annotations.Test;

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

}
