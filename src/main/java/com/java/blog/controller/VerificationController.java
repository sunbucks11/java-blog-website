package com.java.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;

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

import com.java.blog.model.TwoFactorAuthForm;
import com.java.blog.service.UserService;
//import com.warrenstrange.googleauth.GoogleAuthenticator;
import home.test.googauth.GoogleAuthenticator;

@Controller
@RequestMapping("/VerificationController")
public class VerificationController {
	
	private static Logger log = LoggerFactory.getLogger( TwoFactorAuthController.class );
	private static final String BASE_URL = "/admin/auth";
	  
	@Autowired
	private UserService userService;
	  
	
  @RequestMapping("/verification")
  public String verification() {
    return "verification";
  }
  
  
  
  @RequestMapping(method = RequestMethod.POST)
 // public ModelAndView handleTwoFactorAuthInitialisation( HttpServletRequest request, HttpServletResponse response, @ModelAttribute("formBean") TwoFactorAuthForm twoFactorAuthForm, BindingResult bindingResult ) throws ServletException, IOException { 
	  public String handleTwoFactorAuthInitialisation( HttpServletRequest request, HttpServletResponse response, @ModelAttribute("formBean")
      TwoFactorAuthForm twoFactorAuthForm, BindingResult bindingResult ) throws ServletException, IOException {
	  
	  ModelAndView modelAndView = new ModelAndView( "index" );
	  
		HttpSession session = request.getSession( true );
		SecurityContextImpl sci = ( SecurityContextImpl ) session.getAttribute( "SPRING_SECURITY_CONTEXT" );
	    String username = null;

	    if ( sci != null ) 
	    {
	      UserDetails cud = ( UserDetails ) sci.getAuthentication( ).getPrincipal( );
	      username = cud.getUsername( );
		 // Integer code = twoFactorAuthForm.getVerificationCode( );


			String codestr = request.getParameter("code");
			
			long code =	Long.parseLong(codestr);
			
			long t = System.currentTimeMillis();
			
			GoogleAuthenticator ga = new GoogleAuthenticator();
			
			ga.setWindowSize(5);  //should give 5 * 30 seconds of grace
			
			//Get the secret key from the session , you will get it from the db.
			String savedSecret = (String)request.getSession().getAttribute("secretKey");
			
			boolean result = ga.check_code(savedSecret, code, t);
			
			PrintWriter pw = response.getWriter();
			
			if (result) {
				//pw.write("<html><body><h1>Code Verification Successful</h1></body></html>");	
			
				 request.getSession().setAttribute( "isVerified", true );
				 request.getSession().setAttribute( "isAuthenticated", true );
				 //response.sendRedirect("/java-blog-website/index.jsp");
				// response.sendRedirect("/java-blog-website/verification.html");
				 request.getRequestDispatcher("/LoginController").forward(request,response);
				 return "index.html"; 
			} else {
				pw.write("<html><body><h1>Code Verification Unsuccessful</h1></body></html>");
				//response.sendRedirect("/java-blog-website/login.html");
				 return "login.html";
				//request.getRequestDispatcher("/LoginController").forward(request,response);
				//return; 
			}
		  
	    }
		  
		  
		  
		  
		  
		  
		  
	    
		  
		/*  
	    try {
	        //DhpUser dhpUser = getLoggedInDhpUser( request, site.getShortName( ) );
	    	
	        GoogleAuthenticator ga = new GoogleAuthenticator( );
	        boolean isCodeValid = ga.authorize(  userService.findOne(username).getSecretKey( ), code );

	        if ( isCodeValid ) {
	          log.info( "code is correct" );
	          request.getSession( true ).setAttribute( TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_SUCCESS, true );
	          return new ModelAndView( "redirect:/admin/" );
	        } else {
	          return handleInvalidVerificationCode( request );
	        }
	      } catch ( Exception e ) {
	        log.info( e.getMessage( ) );
	        e.printStackTrace( );
	        modelAndView = redirectWithMessage( BASE_URL, "Something went wrong" );
	      }
	    	  
	    }
	    */
   
    return "login.html";
  }
  
  
  
  
  
  private ModelAndView handleInvalidVerificationCode( HttpServletRequest request ) {
	    log.info( "incorrect code" );
	    request.getSession( true ).setAttribute( TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_SUCCESS, false );
	    return redirectWithMessage( BASE_URL, "Verification code is invalid" );
	  }
  
  
  
  private ModelAndView redirectWithMessage( String redirectUrl, String errorMessage ) {
	    ModelAndView mav = new ModelAndView( "redirect:" + redirectUrl );
	    mav.getModelMap( ).put( "message", errorMessage );
	    return mav;
	  }

}
