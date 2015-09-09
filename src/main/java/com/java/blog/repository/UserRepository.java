package com.java.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);
}
