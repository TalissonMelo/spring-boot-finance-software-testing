package com.talissonmelo.finance.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.talissonmelo.finance.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository respository;
	
	@Test
	public void validEmail() {
	
		//cenario
		respository.deleteAll();
		
		//acao
		service.validateEmail("user@gmail.com");		
	}

}
