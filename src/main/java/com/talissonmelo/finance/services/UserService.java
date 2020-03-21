package com.talissonmelo.finance.services;

import com.talissonmelo.finance.entity.User;

public interface UserService {

	public User authenticate(String email, String password);
	
	public User insert(User user);
	
	public void validateEmail(String email);
	
	
}
