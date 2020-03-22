package com.talissonmelo.finance.services;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.repository.UserRepository;
import com.talissonmelo.finance.services.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	UserService service;
	
	@MockBean
	UserRepository respository;
	
	@Before
	public void setUp() {
		service = new UserServiceImpl(respository);
	}
	
	@Test(expected = Test.None.class)
	public void authenticateUserSuccess() {
		
		//cenário
		String email = "email@gmail.com.br";
		String password = "123456";
		
		User user = User.builder().email(email).password(password).id(1l).build();
		Mockito.when(respository.findByEmail(email)).thenReturn(Optional.of(user));
		
		//ação
		User result = service.authenticate(email,password);
		
		//verificação
		Assertions.assertThat(result).isNotNull();
		
	}

	
	@Test(expected = Test.None.class)
	public void validEmail() {	
		//cenário
		Mockito.when(respository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//ação
		service.validateEmail("user@gmail.com");		
	}
	
	@Test(expected = businessRuleException.class)
	public void validEmailFalse() {
	
		//cenário
		Mockito.when(respository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//ação
		service.validateEmail("user@gmail.com");		
	}
}
