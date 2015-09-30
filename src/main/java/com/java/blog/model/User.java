package com.java.blog.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User implements Serializable {

private static final long serialVersionUID = 1L;

  private Long id = -1L;
  private String site;
  private String username;
  private Password password;
  private String email;
  private boolean locked;
  private int attempts = 0;
  private Date lastLoggedIn;
  private Date lastSetPassword;
  private Set<String> roles = new HashSet<String>( );

  public User( ) {
  }

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public Long getId( ) {
    return id;
  }

  public void setId( Long id ) {
    this.id = id;
  }

  @Column(name = "site", length = 3, nullable = false)
  @NotEmpty
  public String getSite( ) {
    return site;
  }

  public void setSite( String site ) {
    this.site = site;
  }

  @Column(name = "username", nullable = false)
  @NotEmpty
  public String getUsername( ) {
    return this.username;
  }

  public void setUsername( String username ) {
    this.username = username;
  }

  @Embedded
  public Password getPassword( ) {
    return password;
  }

  public void setPassword( Password password ) {
    this.password = password;
  }

  @Column(name = "email", nullable = true)
  public String getEmail( ) {
    return this.email;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  @Column(name = "locked", nullable = true)
  public boolean isLocked( ) {
    return locked;
  }

  public void setLocked( boolean locked ) {
    this.locked = locked;
  }

  @Column(name = "attempts", nullable = true)
  public int getAttempts( ) {
    return this.attempts;
  }

  public void setAttempts( int loginAttempts ) {
    this.attempts = loginAttempts;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "last_logged_in", nullable = true)
  public Date getLastLoggedIn( ) {
    return lastLoggedIn;
  }

  public void setLastLoggedIn( Date lastLoggedIn ) {
    this.lastLoggedIn = lastLoggedIn;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "last_set_password", nullable = true)
  public Date getLastSetPassword( ) {
    return lastSetPassword;
  }

  public void setLastSetPassword( Date lastSetPassword ) {
    this.lastSetPassword = lastSetPassword;
  }

  @Transient
  public boolean isPasswordExpired( int afterDays ) {
    if ( lastSetPassword == null ) {
      return true;
    }

    Calendar todayDate = Calendar.getInstance( );
    Calendar lastSetPassAfterDate = Calendar.getInstance( );
    lastSetPassAfterDate.setTime( lastSetPassword );
    lastSetPassAfterDate.add( Calendar.DAY_OF_YEAR, afterDays );
    return lastSetPassAfterDate.before( todayDate );
  }

  @ElementCollection
  @JoinTable(name = "user_to_user_role_join", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role_id", nullable = true)
  public Set<String> getRoles( ) {
    return roles;
  }

  public void setRoles( Set<String> roles ) {
    this.roles = roles;
  }
  public void addRole( String role ) {
    roles.add( role );
  }
  
  public void addRole( Role role ) {
    roles.add( role.name( ) );
  }

  public void clearRoles( ) {
    roles.clear( );
  }
}