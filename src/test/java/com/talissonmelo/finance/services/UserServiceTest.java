package com.talissonmelo.finance.services;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.exceptions.ErrorAuthenticateException;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.repository.UserRepository;
import com.talissonmelo.finance.services.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	//Utilizando Context Spring para criar SPY
	@SpyBean
	UserServiceImpl service;

	@MockBean
	UserRepository respository;

	//Forma Padrão de criar SPY
	/*@Before
	public void setUp() {
		service = Mockito.spy(UserServiceImpl.class);
	}*/

	@Test(expected = Test.None.class)
	public void insertUserSuccess() {

		// cenário
		Mockito.doNothing().when(service).validateEmail(Mockito.anyString());
		User user = User.builder()
				.id(1l)
				.name("Usuário")
				.email("usuario@gmail.com")
				.password("123456")
				.build();
		
		Mockito.when(respository.save(Mockito.any(User.class))).thenReturn(user);
		
		//ação
		User userSave = service.insert(new User());
		
		//verificação
		Assertions.assertThat(userSave).isNotNull();
		Assertions.assertThat(userSave.getId()).isEqualTo(1l);
		Assertions.assertThat(userSave.getName()).isEqualTo("Usuário");
		Assertions.assertThat(userSave.getEmail()).isEqualTo("usuario@gmail.com");
		Assertions.assertThat(userSave.getPassword()).isEqualTo("123456");
		
	}

	@Test(expected = businessRuleException.class)
	public void insertUserError() {
		
		//cenário
		String email = "email@gmail.com";
		User user = User.builder().email(email).build();
		Mockito.doThrow(businessRuleException.class).when(service).validateEmail(email);
		
		//ação
		service.insert(user);
		
		//verificação
		Mockito.verify(respository, Mockito.never()).save(user);
		
		
	}
	@Test(expected = Test.None.class)
	public void authenticateUserSuccess() {

		// cenário
		String email = "email@gmail.com.br";
		String password = "123456";

		User user = User.builder().email(email).password(password).id(1l).build();
		Mockito.when(respository.findByEmail(email)).thenReturn(Optional.of(user));

		// ação
		User result = service.authenticate(email, password);

		// verificação
		Assertions.assertThat(result).isNotNull();

	}

	@Test
	public void authenticateUserLanchError() {

		// cenário
		Mockito.when(respository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		// ação
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@gmail.com", "123456"));
		Assertions.assertThat(exception).isInstanceOf(ErrorAuthenticateException.class)
				.hasMessage("Usuário não encontrado!.");
	}

	@Test
	public void authenticateUserLanchErrorPassword() {

		// cenário
		String password = "123456";
		User user = User.builder().email("email@gmail.com").password(password).build();
		Mockito.when(respository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

		// ação
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@gmail.com", "123"));
		Assertions.assertThat(exception).isInstanceOf(ErrorAuthenticateException.class).hasMessage("Senha inválida!.");

	}

	@Test(expected = Test.None.class)
	public void validEmail() {
		// cenário
		Mockito.when(respository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// ação
		service.validateEmail("user@gmail.com");
	}

	@Test(expected = businessRuleException.class)
	public void validEmailFalse() {

		// cenário
		Mockito.when(respository.existsByEmail(Mockito.anyString())).thenReturn(true);

		// ação
		service.validateEmail("user@gmail.com");
	}
}
