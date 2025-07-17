package com.shop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String email);

	User findByEmail(String email);
}
