package com.java.blog.model;

import com.java.blog.entity.User;

public class TwoFactorAuthForm {

	  private String secretKey;
	  private String barCodeUrl;
	  private Boolean twoFactorAuthInitialised;
	  private Integer verificationCode;
	  
//	  public TwoFactorAuthForm( DhpUser dhpUser ) {
//		    this.secretKey = dhpUser.getSecretKey( );
//		    this.twoFactorAuthInitialised = dhpUser.getTwoFactorAuthInitialised( );
//		  }
	  
	  public TwoFactorAuthForm( User user ) {
		    this.secretKey = user.getSecretKey( );
		    this.twoFactorAuthInitialised = user.getTwoFactorAuthInitialised( );
		  }

		  public TwoFactorAuthForm( ) {

		  }

		  public String getSecretKey( ) {
		    return secretKey;
		  }

		  public void setSecretKey( String secretKey ) {
		    this.secretKey = secretKey;
		  }

		  public String getBarCodeUrl( ) {
		    return barCodeUrl;
		  }

		  public void setBarCodeUrl( String barCodeUrl ) {
		    this.barCodeUrl = barCodeUrl;
		  }

		  public Boolean getTwoFactorAuthInitialised( ) {
		    return twoFactorAuthInitialised;
		  }

		  public void setTwoFactorAuthInitialised( Boolean twoFactorAuthInitialised ) {
		    this.twoFactorAuthInitialised = twoFactorAuthInitialised;
		  }

		  public Integer getVerificationCode( ) {
		    return verificationCode;
		  }

		  public void setVerificationCode( Integer verificationCode ) {
		    this.verificationCode = verificationCode;
		  }
	  
}
