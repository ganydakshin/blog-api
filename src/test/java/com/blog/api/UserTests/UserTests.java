package com.blog.api.UserTests;



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
public class UserTests {

	private PostService postService;
	private UserService userService;
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
