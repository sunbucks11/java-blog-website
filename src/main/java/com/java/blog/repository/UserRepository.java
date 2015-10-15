package com.java.blog.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	User findByName(String name);
	
	@Query("select u from User u where u.email=?1 and u.password=?2")
	User login(String email, String password);
	
	User findByEmailAndPassword(String email, String password);

	User findUserByEmail(String email);
}

/*
package com.java.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);
}
*/