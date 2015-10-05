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
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Component
public class AdminFilter implements Filter {

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
	
		log.info("requestedUri is:" + requestedUri);
		if (requestedUri.matches(".*[css|jpg|png|gif|js]")
				|| requestedUri.contains("admin/auth")
				|| requestedUri.contains("/j_spring_security_logout")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = request.getSession(true);
		
		
		SecurityContextImpl sci = ( SecurityContextImpl ) session.getAttribute( "SPRING_SECURITY_CONTEXT" );
	    String username = null;

	    if ( sci != null ) {
	      UserDetails cud = ( UserDetails ) sci.getAuthentication( ).getPrincipal( );
	      username = cud.getUsername( );
	   
		

		  if (username.equalsIgnoreCase("admin")  && twoFactorAuthenticationEnabled && someoneIsLoggedIn(session)&& !isUserAlreadyAuthenticatedWithTwoFactorAuth(session) && !TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT) 
	   // if (someoneIsLoggedIn(session) && !isUserAlreadyAuthenticatedWithTwoFactorAuth(session)) 
		  {
			// response.sendRedirect("/java-blog-website/TwoFactorAuthController");
			request.getRequestDispatcher("/TwoFactorAuthController").forward(request,response);
			return;
		 }
		

		  if(request.getSession().getAttribute("isVerified") != null){
			  boolean isVerified = (boolean) request.getSession().getAttribute("isVerified");
			  System.out.println("IS VERIFIED: " +  request.getSession().getAttribute("isVerified"));
		  }
		  
		  if(request.getSession().getAttribute("isVerified") != null && (boolean)request.getSession().getAttribute("isVerified") == false){
			request.getRequestDispatcher("/LoginController").forward(request,response);
			return; 
		  }


		 //if(username.equalsIgnoreCase("admin") && TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT && request.getSession().getAttribute("isVerified") == null) 
		 if(username.equalsIgnoreCase("admin") && 
			TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT && 
			request.getSession().getAttribute("isVerified") == null 
			/* && (boolean)request.getSession().getAttribute("isVerified") == true */
//			&& (boolean)request.getSession().getAttribute("isError") == false
			)
		 {
			 request.getRequestDispatcher("/VerificationController").forward(request,response);
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
