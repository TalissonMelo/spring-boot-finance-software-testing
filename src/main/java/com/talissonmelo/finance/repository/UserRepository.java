package com.talissonmelo.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talissonmelo.finance.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
