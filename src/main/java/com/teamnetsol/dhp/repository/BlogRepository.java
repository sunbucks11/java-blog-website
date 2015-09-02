package com.teamnetsol.dhp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamnetsol.dhp.entity.Blog;
import com.teamnetsol.dhp.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

	List<Blog> findByUser(User user);
}
