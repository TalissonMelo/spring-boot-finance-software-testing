package com.talissonmelo.finance.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.talissonmelo.finance.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void testingValueEmail() {
		// cenário
		User user = create();
		entityManager.persist(user);

		// ação
		boolean result = repository.existsByEmail("usuario@gmail.com");

		// verificação
		Assertions.assertThat(result).isTrue();
	}

	@Test
	public void testingValueEmailFalse() {
		// ação
		boolean result = repository.existsByEmail("usuario@gmail.com");

		// verificação
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void insertUser() {
		//cenário
		User user = create(); 
				
		//ação
		User userSalve = repository.save(user);
		
		//verificação
		Assertions.assertThat(userSalve.getId()).isNotNull();
		
	}
	
	@Test
	public void findByEmail() {
		
		//cenário
		User user = create();
		entityManager.persist(user);
		
		//ação
		Optional<User> obj = repository.findByEmail("usuario@gmail.com");
		
		//verificação
		Assertions.assertThat(obj.isPresent()).isTrue();
	}
	
	@Test
	public void findByEmailVoidUser() {		
		//ação
		Optional<User> obj = repository.findByEmail("usuario@gmail.com");
		
		//verificação
		Assertions.assertThat(obj.isPresent()).isFalse();
	}
	
	public static User create() {
		return 	User.builder().name("Usuario").email("usuario@gmail.com").password("123456").build();

	}
}
