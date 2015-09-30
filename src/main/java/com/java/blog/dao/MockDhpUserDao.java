package com.java.blog.dao;

import java.util.ArrayList;
import java.util.List;

import com.java.blog.model.DhpUser;
import com.java.blog.model.DhpUserRole;
import com.java.blog.model.Password;
import com.java.blog.model.Role;
import com.java.blog.model.User;

public class MockDhpUserDao implements UserDao {

  private List<User> users = new ArrayList<User>( );

  public MockDhpUserDao( ) {
	    users.add( createUser( "Janet", "password", false, "LA1", DhpUserRole.ASSESSOR ) );
	    users.add( createUser( "JuniorJanet", "password", false, "LA1", DhpUserRole.ASSESSOR ) );
	    users.add( createUser( "Steve", "password", false, "LA1", DhpUserRole.ACCOUNT_ADMINISTRATOR ) );
  }

  public User loadUserByUsernameAndSite( String username, String site ) {
    for ( User user : users ) {
      if ( user.getUsername( ).equals( username ) && user.getSite( ).equals( site ) ) {
        return user;
      }
    }
    return null;
  }

  private DhpUser createUser( String username, String password, boolean locked, String site, DhpUserRole... roles ) {
    DhpUser user = new DhpUser( );
    user.setUsername( username );
    user.setPassword( new Password( password ) );
    user.setEmail( username + "@teamnetsol.com" );
    user.setLocked( locked );
    user.setSite( site );
    for ( Role role : roles ) {
      user.addRole( role );
    }
    return user;
  }

//  public List<User> listUsers( UserQueryParams params ) {
//    return null;
//  }
//
//  public int countUsers( UserQueryParams params ) {
//    return 0;
//  }

  public Long saveUser( User user ) {
    return null;
  }

  public User loadUser( Long id, String site ) {
    return null;
  }

}
