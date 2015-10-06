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
import com.java.blog.entity.User;
import com.java.blog.model.DhpUser;
import com.java.blog.model.TwoFactorAuthForm;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;



@Controller
@RequestMapping("/TwoFactorAuthController")
//@RequestMapping("/admin/auth")
public class TwoFactorAuthController {

	  private static Logger log = LoggerFactory.getLogger( TwoFactorAuthController.class );

	  public static final String TWO_FACTOR_AUTHENTICATION_SUCCESS = "TWO_FACTOR_AUTHENTICATION";
	  
	  public static boolean isResetTwoFactorAuth = false;
	  public static boolean isVerificationRequired = true; 
	  
	  public static boolean TWO_FACTOR_AUTHENTICATION_INT = false;
	  
	  public static GoogleAuthenticatorKey SecretKey; 
	  
	  public static boolean TWO_FACTOR_VERIFIED = false;
	  private static final String BASE_URL = "/admin/auth";

	 // @Autowired
	 // private UserRepository userRepository;
	  
	  @Autowired
	  private UserService userService;
	  
	  private UserDao userDao;
	  
	  @RequestMapping(method = RequestMethod.GET)
	  public ModelAndView handleGetTwoFactorAuth( HttpServletRequest request, HttpServletResponse response ) throws IOException {
	   
	    ModelAndView modelAndView = new ModelAndView("barcode");

		HttpSession session = request.getSession( true );
		SecurityContextImpl sci = ( SecurityContextImpl ) session.getAttribute( "SPRING_SECURITY_CONTEXT" );
	    String username = null;

	    if ( sci != null ) {
	      UserDetails cud = ( UserDetails ) sci.getAuthentication( ).getPrincipal( );
	      username = cud.getUsername( );
	      userService.findOne(username);
	      TwoFactorAuthForm twoFactorAuthForm = new TwoFactorAuthForm(  userService.findOne(username));

	      System.out.println("Current User: " +  userService.findOne(username).getName());
	        
	      try {
	      	    	  
	      //if( userService.findOne(username).getTwoFactorAuthInitialised() == null || ! userService.findOne(username).getTwoFactorAuthInitialised()){
	    	if( userService.findOne(username).getTwoFactorAuthInitialised() == null && !TWO_FACTOR_AUTHENTICATION_INT){
	    	  GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator( );

	          final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials( );
	          final String secret = key.getKey( );

	          
	          //String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL( currentUser.getName(), currentUser.getEmail( ),key );
				String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
						+  userService.findOne(username).getName() + "?secret=" + secret;
	          				
	           modelAndView.getModelMap( ).put( "secretKey", secret );
	          modelAndView.getModelMap( ).put( "barCodeUrl", otpAuthURL );
	          modelAndView.getModelMap( ).put( "initAuth", true );
	          //userService.findOne(username).setTwoFactorAuthInitialised(true);
			//  request.getSession().setAttribute("secretKey", secret);
			  
	          // NOT SURE WHICH ERROR THIS REPRESENTS
			  request.getSession().setAttribute("isError", false);
			  
			  userService.findOne(username).setSecretKey(secret);	
			  this.SecretKey = key;
	          TWO_FACTOR_AUTHENTICATION_INT = true; 
	          TwoFactorAuthController.isResetTwoFactorAuth = false; 
	      }
	      else
	      {
	    	  //String savedSecret = (String)request.getSession().getAttribute("secretKey");
	    	  request.getRequestDispatcher("/VerificationController").forward(request,response);
	      }
	      
	      modelAndView.getModelMap( ).put( "formBean", twoFactorAuthForm );
	    } catch ( Exception e ) {
	        e.printStackTrace( );
	        modelAndView = redirectWithMessage( "/", "Something went wrong while authenticating user" );
	      }	      
	    }
	    
	    return modelAndView;
	  }

	      /*
	      
			request.getSession().setAttribute("isAuthenticated", true);

			if (request.getSession().getAttribute("isAdmin") == null
					&& request.getSession().getAttribute("isVerified") == null) 
			{
				// user want to set up 2fa
				final GoogleAuthenticatorKey key = googleAuthenticator
						.createCredentials();
				final String secret = key.getKey();
				request.getSession().setAttribute("secretKey", secret);
				String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
						+ username + "?secret=" + secret;

				// modelAndView.getModelMap().put("initAuth", true);
				// modelAndView.getModelMap().put("secretKey", secret);
				modelAndView.getModelMap().put("barCodeUrl", otpAuthURL);

				request.getSession().setAttribute("isAdmin", true);
				//TWO_FACTOR_AUTHENTICATION_SUCCESS = true; 
				//request.getSession( true ).setAttribute( TWO_FACTOR_AUTHENTICATION_SUCCESS, true );
				userRepository.findByName(username).setTwoFactorAuthInitialised(true);
				
				// request.getSession().setAttribute( "username",
				// request.getParameter("j_username") );
				return modelAndView;
			}

			//if (request.getSession().getAttribute("isVerified") == null) 
			if (request.getSession( true ).getAttribute(TWO_FACTOR_AUTHENTICATION_SUCCESS) == "true") 
			{
				return new ModelAndView("redirect:/verification.html");
			} else {
				return new ModelAndView("redirect:/index.html");
			}
	    }
	      
	    return modelAndView;
	      
	    }
	  
	  */
		
		
	    /*
		if (checkCredentials(username, "admin")) 
		{
			request.getSession().setAttribute("isAuthenticated", true);

			if (request.getSession().getAttribute("isAdmin") == null
					&& request.getSession().getAttribute("isVerified") == null) 
			{
				// user want to set up 2fa
				final GoogleAuthenticatorKey key = googleAuthenticator
						.createCredentials();
				final String secret = key.getKey();
				request.getSession().setAttribute("secretKey", secret);
				String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
						+ username + "?secret=" + secret;

				// modelAndView.getModelMap().put("initAuth", true);
				// modelAndView.getModelMap().put("secretKey", secret);
				modelAndView.getModelMap().put("barCodeUrl", otpAuthURL);

				request.getSession().setAttribute("isAdmin", true);
				// request.getSession().setAttribute( "username",
				// request.getParameter("j_username") );
				return modelAndView;
			}

			if (request.getSession().getAttribute("isVerified") == null) {
				return new ModelAndView("redirect:/verification.html");
			} else {
				return new ModelAndView("redirect:/index.html");
			}
		}
	  
		else {
			return new ModelAndView("redirect:/login.html");
		}
		*/
	    
	    
	    
	    /*
	    try {
 
		  ModelAndView modelAndView = new ModelAndView( "auth" );
		  DhpUser dhpUser = getLoggedInDhpUser( request, "LA1" );
		  DhpUser dhpUser = new MockDhpUserDao();
	      TwoFactorAuthForm twoFactorAuthForm = new TwoFactorAuthForm( dhpUser );

	      modelAndView.getModelMap( ).put( "title", "Second Step Verification Required" );

	      if ( !dhpUser.getTwoFactorAuthInitialised( ) ) {
	        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator( );

	        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials( );
	        final String secret = key.getKey( );

	        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL( dhpUser.getUsername( ), dhpUser.getEmail( ),key );

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
	     */

	    //return modelAndView;
	//  }
	  
	  
	  
	  
	  @RequestMapping(method = RequestMethod.POST, value = "verify")
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
	  
	  
	  
	  
	  
	  
	  @RequestMapping(method = RequestMethod.POST, value = "init")
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
