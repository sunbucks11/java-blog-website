package com.java.blog.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java.blog.entity.User;

//public interface UserRepository extends JpaRepository<User, Integer> {
public interface UserRepository extends JpaRepository<User, Serializable>{
	@Query("select u from User u where u.email=?1 and u.password=?2")
	User login(String email, String password);
	
	User findByName(String name);
	
	User findUserByEmail(String email);
}
