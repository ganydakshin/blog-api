package com.blog.api.service;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class IdGenerator {
	
	long range;
	Random random;

	@PostConstruct
	public void init() {
		range = 1234567L;
		random = new Random();
	}
	
	public Long generate() {
		long id = (long)(random.nextDouble()*range);
		return new Long(id);
	}

}
