package com.teamnetsol.dhp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamnetsol.dhp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);
}
