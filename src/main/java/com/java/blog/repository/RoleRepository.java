package com.java.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByName(String name);

}
