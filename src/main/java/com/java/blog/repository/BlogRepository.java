package com.java.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.blog.entity.Blog;
import com.java.blog.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

	List<Blog> findByUser(User user);
}
