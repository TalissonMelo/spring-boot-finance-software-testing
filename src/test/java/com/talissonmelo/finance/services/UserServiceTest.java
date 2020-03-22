package com.talissonmelo.finance.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository respository;
	
	@Test(expected = Test.None.class)
	public void validEmail() {
	
		//cenário
		respository.deleteAll();
		
		//ação
		service.validateEmail("user@gmail.com");		
	}
	
	@Test(expected = businessRuleException.class)
	public void validEmailFalse() {
	
		//cenário
		User user = User.builder().name("Usuario").email("user@gmail.com").build();
		respository.save(user);
		
		//ação
		service.validateEmail("user@gmail.com");		
	}

}
