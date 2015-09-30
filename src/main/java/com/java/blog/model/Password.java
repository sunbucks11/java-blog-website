package com.java.blog.model;

import java.io.Serializable;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Password implements Serializable {

  private static Random rnd = new Random( );

  public static final int DEFAULT_SALT_LENGTH = 10;
  public static final String DEFAULT_ALGORITHM = "SHA-256";
  public static final int DEFAULT_DIGEST_LENGTH = 32;

  private String hashedPassword;
  private byte [] salt;
  private String algorithm = DEFAULT_ALGORITHM;
  
  public Password( ) {
  }

  public Password( String plainPassword ) {
    this.salt = createSalt( );
    this.setPlainPassword( plainPassword );
  }

  public Password( String plainPassword, byte [] salt ) {
    this.salt = salt;
    this.setPlainPassword( plainPassword );
  }

  public void setAlgorithm( String algorithm ) {
    this.algorithm = algorithm;
  }

  public void setHashedPassword( String password ) {
    this.hashedPassword = password;
  }

  public void setPlainPassword( String plainPassword ) {
    this.hashedPassword = toHex( hash( plainPassword.getBytes( ) ) );
  }

  public void setSalt( byte [] salt ) {
    this.salt = salt;
  }

  @Column(name="password_hash", nullable=true)
  public String getHashedPassword( ) {
    return this.hashedPassword;
  }

  @Column(name="salt", nullable=true)
  public byte [] getSalt( ) {
    return salt;
  }

  public boolean checkPassword( String confirmPassword ) {
    byte[] hashedBytes = hashedPassword.getBytes( );
    byte[] confirmBytes = new Password( confirmPassword, salt ).getHashedPassword( ).getBytes( );
    return MessageDigest.isEqual( hashedBytes, confirmBytes );
  }
  
  protected byte [] hash( byte [] input ) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance( algorithm );
      md.update( input );
      md.update( salt );
      return digest( md );
    } catch ( NoSuchAlgorithmException e ) {
      throw new RuntimeException( "Failed to create password hash", e );
    } catch ( DigestException e ) {
      throw new RuntimeException( "Failed to create password hash", e );
    }
  }

  protected byte [] digest( MessageDigest md ) throws DigestException {
    byte [] raw = new byte[ DEFAULT_DIGEST_LENGTH ];
    md.digest( raw, 0, DEFAULT_DIGEST_LENGTH );
    return raw;
  }

  private byte [] createSalt( ) {
    byte [] salt = new byte[ DEFAULT_SALT_LENGTH ];
    rnd.nextBytes( salt );
    return salt;
  }

  private String toHex( byte [] input ) {
    StringBuffer hexString = new StringBuffer( );
    for ( byte b : input ) {
      hexString.append( Integer.toHexString( 0xFF & b ) );
    }
    return hexString.toString( );
  }
}
