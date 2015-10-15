package com.java.blog.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.blog.entity.Blog;
import com.java.blog.entity.Item;
import com.java.blog.entity.Role;
import com.java.blog.entity.User;
import com.java.blog.repository.BlogRepository;
import com.java.blog.repository.ItemRepository;
import com.java.blog.repository.RoleRepository;
import com.java.blog.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	
	// CRUD
	public User create(User user) {
		return userRepository.save(user);
	}
	
	public void deleteUser(int id) {
		userRepository.delete(id);
	}
	
	public User update(User user) {
		return userRepository.save(user);
	}
	
	//@SuppressWarnings("unchecked")
	public void save(User user) {
		user.setEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));

		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByName("ROLE_USER"));
		//user.setRoles(roles);
		//user.setRoles((Set<Role>) roles);
		userRepository.save(user);
	}
	
	
	// Find	
	public User findUserById(int id) {
		return userRepository.findOne(id);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Transactional
	public User findOne(String username) {
		return userRepository.findByName(username);
	}
	
	@Transactional
	public User findOneWithBlogs(int id) {
		User user = findUserById(id);	
		List<Blog> blogs = blogRepository.findByUser(user);
		for (Blog blog : blogs) {
			List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0, 10, Direction.DESC, "publishedDate"));
			blog.setItems(items);
		}
		user.setBlogs(blogs);
		return user;
	}
	
	public User findOneWithBlogs(String name) {
		User user = userRepository.findByName(name);
		return findOneWithBlogs(user.getId());
	}	
		
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	public User login(String email, String password) {
		return userRepository.findByEmailAndPassword(email,password);
	}
}

/*
package com.java.blog.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.blog.entity.Blog;
import com.java.blog.entity.Item;
import com.java.blog.entity.Role;
import com.java.blog.entity.User;
import com.java.blog.repository.BlogRepository;
import com.java.blog.repository.ItemRepository;
import com.java.blog.repository.RoleRepository;
import com.java.blog.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private RoleRepository roleRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(int id) {
		return userRepository.findOne(id);
	}

	@Transactional
	public User findOneWithBlogs(int id) {
		User user = findOne(id);	
		List<Blog> blogs = blogRepository.findByUser(user);
		for (Blog blog : blogs) {
			List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0, 10, Direction.DESC, "publishedDate"));
			blog.setItems(items);
		}
		user.setBlogs(blogs);
		return user;
	}

	public void save(User user) {
		user.setEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));

		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByName("ROLE_USER"));
		user.setRoles(roles);
		userRepository.save(user);
	}

	public User findOneWithBlogs(String name) {
		User user = userRepository.findByName(name);
		return findOneWithBlogs(user.getId());
	}

	public void delete(int id) {
		userRepository.delete(id);
	}

	@Transactional
	public User findOne(String username) {
		return userRepository.findByName(username);
	}
}
*/
