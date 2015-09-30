package com.java.blog.dao;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.java.blog.model.NqUser;


@Validated
public interface UserDao {

  public Long saveUser( @Valid NqUser user );
  public NqUser loadUser( Long id, String site );
  public NqUser loadUserByUsernameAndSite( String username, String site );
  //public List<User> listUsers( UserQueryParams params );
 //public int countUsers( UserQueryParams params );

}
