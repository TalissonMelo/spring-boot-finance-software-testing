package com.talissonmelo.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talissonmelo.finance.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	//Query methods 
	//Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
