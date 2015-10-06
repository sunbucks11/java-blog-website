package com.java.blog.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.java.blog.entity.User;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Component
public class AdminFilter implements Filter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository; 
	
	private static Logger log = LoggerFactory.getLogger(AdminFilter.class);
	private boolean twoFactorAuthenticationEnabled = true; // XXX will this be
															// configurable?

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void  doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		log.info("adminFilter.doFilter executed");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String requestedUri = request.getRequestURL().toString();
		
		// allow all resources to get passed this filter
		
		System.out.println("REQUESTED URL: " + requestedUri);
		
	
		log.info("requestedUri is:" + requestedUri);
		if (requestedUri.matches(".*[css|jpg|png|gif|js]")
				|| requestedUri.contains("admin/auth")
//				|| requestedUri.contains("/j_spring_security_logout")
				) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = request.getSession(true);
		
		  if(requestedUri.contains("/ErrorController/Reset"))
		  {
			  request.getRequestDispatcher("/ResetController").forward(request,response);
			  return; 
		  }
		  
		  if(requestedUri.contains("/java-blog-website/index.html") || requestedUri.contains("/java-blog-website/index"))
		  {
			  request.getRequestDispatcher("/IndexController").forward(request,response);
			  return; 
		  }
		  
			if( requestedUri.contains("/j_spring_security_logout")){
				TwoFactorAuthController.isVerificationRequired = true; 
				chain.doFilter(request, response);
				return; 
			}
		  
			
			
			if(requestedUri.contains("/j_spring_security_check")){
				//TwoFactorAuthController.isVerificationRequired = true; 
				chain.doFilter(request, response);
				return; 
			}
		  
		  
		
		SecurityContextImpl sci = ( SecurityContextImpl ) session.getAttribute( "SPRING_SECURITY_CONTEXT" );
	    String username = null;

	    if ( sci != null ) {
	      UserDetails cud = ( UserDetails ) sci.getAuthentication( ).getPrincipal( );
	      username = cud.getUsername( );
 
		  if(request.getSession().getAttribute("isVerifiedError") != null && (boolean)request.getSession().getAttribute("isVerifiedError") == true)
		  {
			  				
			  
			  if(TwoFactorAuthController.isResetTwoFactorAuth)
			  {
			    	twoFactorAuthenticationEnabled = true;
			    	TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT = false; 
			    	session.invalidate();
				    chain.doFilter(request, response);
				    return; 
			  } 
		  }


		  System.out.println("USERNAME: " + username.equalsIgnoreCase("admin"));
		  System.out.println("twoFactorAuthenticationEnabled:@ " + twoFactorAuthenticationEnabled);
		  System.out.println("someoneIsLoggedIn: " +  someoneIsLoggedIn(session));
		  System.out.println("isUserAlreadyAuthenticatedWithTwoFactorAuth: " + isUserAlreadyAuthenticatedWithTwoFactorAuth(session));
		  System.out.println("TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT: " + TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT );
		  
		  
		  if (username.equalsIgnoreCase("admin")  && 
				  twoFactorAuthenticationEnabled  && 
				  someoneIsLoggedIn(session)	  && 
				  !isUserAlreadyAuthenticatedWithTwoFactorAuth(session) && 
				  !TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT) 
		  {
			request.getRequestDispatcher("/TwoFactorAuthController").forward(request,response);
			return;
		 }
		
		  
		  System.out.println("isVerificationRequired: " + request.getSession().getAttribute("isVerificationRequired"));

		 //if(username.equalsIgnoreCase("admin") && TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT && request.getSession().getAttribute("isVerified") == null) 
		 if(username.equalsIgnoreCase("admin") && 
			TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT && 
			TwoFactorAuthController.isVerificationRequired
			//request.getSession().getAttribute("isVerificationRequired") == null ||
			//(boolean)request.getSession().getAttribute("isVerificationRequired")  

			)
		 {
			 request.getRequestDispatcher("/VerificationController").forward(request,response);
			 request.getSession().setAttribute("isVerificationRequired", false);
			 return; 
		 }
	    }
		  
		log.info("adminFilter.doFilter skipping to next filter");
		chain.doFilter(req, res);
	}

	private boolean someoneIsLoggedIn(HttpSession session) {
		try {
			SecurityContextImpl sci = (SecurityContextImpl) session
					.getAttribute("SPRING_SECURITY_CONTEXT");
			String username = null;
			if (sci != null) {
				UserDetails cud = (UserDetails) sci.getAuthentication()
						.getPrincipal();
				username = cud.getUsername();
			}
			log.info("LoggedInUser is " + username);

			if (username != null && !username.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return false;
	}

	
	private boolean isUserAlreadyAuthenticatedWithTwoFactorAuth(
			HttpSession session) throws IOException, ServletException {
		Object twoFactorSuccess = session.getAttribute(LoginController.TWO_FACTOR_AUTHENTICATION_SUCCESS);
		
		if (twoFactorSuccess != null && twoFactorSuccess instanceof Boolean
				&& (Boolean) twoFactorSuccess) {
			return true;
		}
		return false;
	}
	
	
	  public void destroy( ) {

	  }
	  
	  public void setTwoFactorAuthenticationEnabled( boolean twoFactorAuthenticationEnabled ) {
		    this.twoFactorAuthenticationEnabled = twoFactorAuthenticationEnabled;
		  }

}
