package com.java.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.blog.dao.MockDhpUserDao;
import com.java.blog.dao.UserDao;
import com.java.blog.model.DhpUser;
import com.java.blog.model.TwoFactorAuthForm;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;


@Controller
@RequestMapping("/auth")
public class TwoFactorAuthController {

	  private static Logger log = LoggerFactory.getLogger( TwoFactorAuthController.class );

	  public static final String TWO_FACTOR_AUTHENTICATION_SUCCESS = "TWO_FACTOR_AUTHENTICATION";
	  private static final String BASE_URL = "/admin/auth";
	  
	  
	  private UserDao userDao;

//	  @Autowired
//	  public void setUserDao( UserDao userDao ) {
//	    this.userDao = userDao;
//	  }
	  
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public ModelAndView handleGetTwoFactorAuth( HttpServletRequest request, HttpServletResponse response ) throws IOException {
	    ModelAndView modelAndView = new ModelAndView( "auth" );
	    try {
	    	
	      DhpUser dhpUser = getLoggedInDhpUser( request, "LA1" );
	      TwoFactorAuthForm twoFactorAuthForm = new TwoFactorAuthForm( dhpUser );

	      modelAndView.getModelMap( ).put( "title", "Second Step Verification Required" );

	      if ( !dhpUser.getTwoFactorAuthInitialised( ) ) {
	        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator( );

	        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials( );
	        final String secret = key.getKey( );

	        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL( dhpUser.getUsername( ), dhpUser.getEmail( ),
	            key );

	        modelAndView.getModelMap( ).put( "secretKey", secret );
	        modelAndView.getModelMap( ).put( "barCodeUrl", otpAuthURL );
	        modelAndView.getModelMap( ).put( "initAuth", true );
	      } else {
	        modelAndView.getModelMap( ).put( "initAuth", false );
	      }
	      modelAndView.getModelMap( ).put( "formBean", twoFactorAuthForm );

	    } catch ( Exception e ) {
	      e.printStackTrace( );
	      modelAndView = redirectWithMessage( "/", "Something went wrong while authenticating user" );
	    }

	    return modelAndView;
	  }
	  
	  
	  
	  
	  @RequestMapping(method = RequestMethod.POST, value = "/verify")
	  public ModelAndView handleVerification( HttpServletRequest request, HttpServletResponse response, @ModelAttribute("formBean")
	      TwoFactorAuthForm twoFactorAuthForm, BindingResult bindingResult ) throws ServletException, IOException {

	    log.info( "handleVerification" );
	    Integer code = twoFactorAuthForm.getVerificationCode( );

	    ModelAndView modelAndView = new ModelAndView( "auth" );
	    try {
	      DhpUser dhpUser = getLoggedInDhpUser( request, "LA1" );
	      GoogleAuthenticator ga = new GoogleAuthenticator( );
	      boolean isCodeValid = ga.authorize( dhpUser.getSecretKey( ), code );

	      if ( isCodeValid ) {
	        log.info( "code is correct" );
	        request.getSession( true ).setAttribute( TWO_FACTOR_AUTHENTICATION_SUCCESS, true );
	        return new ModelAndView( "redirect:/admin/" );
	      } else {
	        return handleInvalidVerificationCode( request );
	      }
	    } catch ( Exception e ) {
	      log.info( e.getMessage( ) );
	      e.printStackTrace( );
	      modelAndView = redirectWithMessage( BASE_URL, "Something went wrong" );
	    }

	    return modelAndView;
	  }
	  
	  
	  
	  
	  
	  
	  @RequestMapping(method = RequestMethod.POST, value = "/init")
	  public ModelAndView handleTwoFactorAuthInitialisation( HttpServletRequest request, HttpServletResponse response, @ModelAttribute("formBean")
	      TwoFactorAuthForm twoFactorAuthForm, BindingResult bindingResult ) throws ServletException, IOException {

	    log.info( "handleTwoFactorAuthInitialisation" );

	    Integer code = twoFactorAuthForm.getVerificationCode( );
	    String secretKey = twoFactorAuthForm.getSecretKey( );

	    ModelAndView modelAndView = new ModelAndView( "auth" );
	    try {
	      DhpUser dhpUser = getLoggedInDhpUser( request, "LA1" );
	      GoogleAuthenticator ga = new GoogleAuthenticator( );
	      boolean isCodeValid = ga.authorize( secretKey, code );

	      log.info( "isCodeValid :" + isCodeValid );

	      if ( isCodeValid ) {
	    	// userDao = new MockUserDao( );
	    	  userDao = new MockDhpUserDao( );
	        log.info( "code is correct" );
	        dhpUser.setSecretKey( secretKey );
	        dhpUser.setTwoFactorAuthInitialised( true );
	        userDao.saveUser( dhpUser );
	        request.getSession( true ).setAttribute( TWO_FACTOR_AUTHENTICATION_SUCCESS, true );
	        modelAndView = redirectWithMessage( "/admin/", "SUCCESS" );
	      } else {
	        return handleInvalidVerificationCode( request );
	      }
	    } catch ( Exception e ) {
	      log.info( e.getMessage( ) );
	      e.printStackTrace( );
	      modelAndView = redirectWithMessage( BASE_URL, "Something Went Wrong" );
	    }

	    return modelAndView;
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  public DhpUser getLoggedInDhpUser( HttpServletRequest request, String siteId ) throws Exception {
		    HttpSession session = request.getSession( true );

		    SecurityContextImpl sci = ( SecurityContextImpl ) session.getAttribute( "SPRING_SECURITY_CONTEXT" );
		    String username = null;
		    if ( sci != null ) {
		      UserDetails cud = ( UserDetails ) sci.getAuthentication( ).getPrincipal( );
		      username = cud.getUsername( );
		    }

		    if ( username != null && !username.isEmpty( ) ) {
		      return ( DhpUser ) userDao.loadUserByUsernameAndSite( username, siteId );
		    }
		    throw new Exception( "DhpUser not found" );
		  }
	  
	  
	  private ModelAndView redirectWithMessage( String redirectUrl, String errorMessage ) {
		    ModelAndView mav = new ModelAndView( "redirect:" + redirectUrl );
		    mav.getModelMap( ).put( "message", errorMessage );
		    return mav;
		  }
	  
	  
	  private ModelAndView handleInvalidVerificationCode( HttpServletRequest request ) {
		    log.info( "incorrect code" );
		    request.getSession( true ).setAttribute( TWO_FACTOR_AUTHENTICATION_SUCCESS, false );
		    return redirectWithMessage( BASE_URL, "Verification code is invalid" );
		  }
	  
}
