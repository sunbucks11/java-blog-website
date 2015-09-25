package com.java.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.blog.entity.User;
import com.java.blog.repository.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
public class LoginController {
	
	@Autowired
	private UserRepository userRepository;
	
	boolean is2faSetup  = true;
	
	
	//@RequestMapping("/login")
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    ModelAndView modelAndView = new ModelAndView( "barcode" );
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator( );
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
				
		
		//if (username != null && password != null) 
		//{
			if(checkCredentials(username, password))
			{
				
				if(is2faSetup)
				{
				     // user want to set up 2fa 				
			         final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials( );
			         final String secret = key.getKey( ); 
			         request.getSession().setAttribute( "secretKey", secret );
			     	 String otpAuthURL =  "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/" +username+"?secret="+secret;
			     	 
			         modelAndView.getModelMap( ).put( "secretKey", secret );
			         modelAndView.getModelMap( ).put( "barCodeUrl", otpAuthURL );
			         modelAndView.getModelMap( ).put( "initAuth", true ); 		
			         is2faSetup = false; 
			         
			         //forward to verify code
				}
				else
				{
				    // forward to verify code				
					//request.setAttribute("username", username);
					//request.getRequestDispatcher("/auth.jsp").forward(request,response);	
					//request.getRequestDispatcher("/barcode.jsp").forward(request,response);
					
					//System.out.println("R");
					//modelAndView.addObject( "redirect:/account.html"); 
				    return new ModelAndView("redirect:/verification.html");
				}
			//}
							
		}
		else 
		{
			
			//request.setAttribute("error", "Unknown user, please try again");
			//request.getRequestDispatcher("/index.jsp").forward(request,response);
		     System.out.println("Unknown user, please try again");
		}
		
		
		
		
		
		
		
		
		

         
         
         return modelAndView;	
	}


	
	

	
	private boolean checkCredentials(String username, String password) {
		User user = userRepository.findByName(username); 
		if(user != null){
			
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
		if(user.getName() == username && encoder.matches(password, user.getPassword())){
				return true; 
			}
		}	
		return false;
	}
}
