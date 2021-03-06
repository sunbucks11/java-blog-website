package com.java.blog.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.java.blog.annotation.UniqueUsername;

@Entity
@Table(name="app_user")
public class User {

	@Id
	//@GeneratedValue
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Size(min=3, message="Name must be at least 3 characters!")
	@Column(unique = true)
	@UniqueUsername(message="Such username already exists!")
	private String name;

	@Size(min=1, message="Invalid email address!")
	@Email(message="Invalid email address!")
	@Column(nullable=false, unique=true)
	private String email;
	
	@Size(min=5, message="Password must be at least 5 characters!")
	@Column(nullable=false)
	private String password;
	
	private Date dob;

	private boolean enabled;
	
	private String secretKey;
	
	private Boolean twoFactorAuthInitialised;
	
	private boolean isAuthenticated; 
	
	private boolean isVerified; 
	
	private boolean isVerifiedError; 
	
	private boolean isResetTwoFactorAuth;
	
	

	public boolean isResetTwoFactorAuth() {
		return isResetTwoFactorAuth;
	}

	public void setResetTwoFactorAuth(boolean isResetTwoFactorAuth) {
		this.isResetTwoFactorAuth = isResetTwoFactorAuth;
	}

	public boolean isVerifiedError() {
		return isVerifiedError;
	}

	public void setVerifiedError(boolean isVerifiedError) {
		this.isVerifiedError = isVerifiedError;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/* ORIGINAL CODE
	@ManyToMany
	@JoinTable
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}	
	*/
	
	/******* FOR TEST PURPOSE ********/
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private Set<Role> roles = new HashSet<>();
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/********************************/
		

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Blog> blogs;
	
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Boolean getTwoFactorAuthInitialised() {
		return twoFactorAuthInitialised;
	}

	public void setTwoFactorAuthInitialised(Boolean twoFactorAuthInitialised) {
		this.twoFactorAuthInitialised = twoFactorAuthInitialised;
	}


	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
