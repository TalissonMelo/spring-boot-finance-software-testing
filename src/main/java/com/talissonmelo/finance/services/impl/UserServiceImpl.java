package com.talissonmelo.finance.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talissonmelo.finance.entity.User;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User insert(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validateEmail(String email) {
		boolean exist = repository.existsByEmail(email);
		if (exist) {
			throw new businessRuleException("Já existe um usuário cadastrado com este email!.");
		}

	}

}
