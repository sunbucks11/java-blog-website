package com.java.blog.dao;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.java.blog.model.User;


@Validated
public interface UserDao {

  public Long saveUser( @Valid User user );
  public User loadUser( Long id, String site );
  public User loadUserByUsernameAndSite( String username, String site );
  //public List<User> listUsers( UserQueryParams params );
 //public int countUsers( UserQueryParams params );

}
