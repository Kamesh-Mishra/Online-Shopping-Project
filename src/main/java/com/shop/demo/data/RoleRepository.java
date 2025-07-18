package com.shop.demo.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.demo.model.Role;



public interface RoleRepository extends JpaRepository<Role,Long> {
	
    Optional<Role> findByName(String role);
}
