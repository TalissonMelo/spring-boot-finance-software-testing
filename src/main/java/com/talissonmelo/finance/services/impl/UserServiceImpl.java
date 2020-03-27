package com.talissonmelo.finance.services.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talissonmelo.finance.entity.User;
import com.talissonmelo.finance.exceptions.ErrorAuthenticateException;
import com.talissonmelo.finance.exceptions.businessRuleException;
import com.talissonmelo.finance.repository.UserRepository;
import com.talissonmelo.finance.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public User authenticate(String email, String password) {
		Optional<User> user = repository.findByEmail(email);
		
		if(!user.isPresent()) {
			throw new ErrorAuthenticateException("Usuário não encontrado!.");
		}
		
		if(!user.get().getPassword().equals(password)) {
			throw new ErrorAuthenticateException("Senha inválida!.");
		}
		return user.get();
	}

	@Override
	@Transactional
	public User insert(User user) {
		validateEmail(user.getEmail());
		return repository.save(user);
	}

	@Override
	public void validateEmail(String email) {
		boolean exist = repository.existsByEmail(email);
		if (exist) {
			throw new businessRuleException("Já existe um usuário cadastrado com este email!.");
		}

	}

	@Override
	public Optional<User> findUserById(Long id) {
		return repository.findById(id);
	}

}
