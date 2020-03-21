package com.talissonmelo.finance.repository;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.talissonmelo.finance.entity.User;

@SpringBootTest
public class UserRepositoryTest implements CommandLineRunner {

	@Autowired
	private UserRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		
		//cenario
		User user = User.builder().name("usuario").email("usuario@gmail.com").build();
		repository.save(user);
		
		//ação
		boolean result = repository.existsByEmail(user.getEmail());
		
		
		//verificação
		Assertions.assertThat(result).isTrue();
	}

}
