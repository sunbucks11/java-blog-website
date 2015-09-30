package com.java.blog.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class DhpUser extends NqUser{

  private static final long serialVersionUID = 3915942213394225973L;
  private Address address;
  private String organization;
  private String secretKey;
  private Boolean twoFactorAuthInitialised;

  @Embedded
  public Address getAddress( ) {
    return address;
  }

  public void setAddress( Address address ) {
    this.address = address;
  }

  @Column(name = "organization")
  public String getOrganization( ) {
    return organization;
  }

  public void setOrganization( String organization ) {
    this.organization = organization;
  }

  @Column(name = "secretKey")
  public String getSecretKey( ) {
    return secretKey;
  }

  public void setSecretKey( String secretKey ) {
    this.secretKey = secretKey;
  }

  @Column(name = "twoFactorAuthInitialised")
  public Boolean getTwoFactorAuthInitialised( ) {
    return twoFactorAuthInitialised;
  }

  public void setTwoFactorAuthInitialised( Boolean twoFactorAuthInitialised ) {
    this.twoFactorAuthInitialised = twoFactorAuthInitialised;
  }
  
}
