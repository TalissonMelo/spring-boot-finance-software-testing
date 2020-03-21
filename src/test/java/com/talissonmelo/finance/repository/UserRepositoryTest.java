package com.talissonmelo.finance.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.talissonmelo.finance.entity.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void testingValueEmail() {
		// cenario
		User user = User.builder().name("usuario").email("usuario@gmail.com").build();
		repository.save(user);

		// ação
		boolean result = repository.existsByEmail(user.getEmail());

		// verificação
		Assertions.assertThat(result).isTrue();
	}
}
