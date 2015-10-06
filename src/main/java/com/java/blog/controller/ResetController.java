package com.java.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.blog.entity.User;
import com.java.blog.repository.UserRepository;
import com.java.blog.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
@RequestMapping("/ResetController")
public class ResetController {
	
	@RequestMapping(method = RequestMethod.POST)
	public String indexPost(Model model, HttpServletRequest request, HttpServletResponse response) {
	  TwoFactorAuthController.isResetTwoFactorAuth = true; 
	  TwoFactorAuthController.isVerificationRequired = true; 
			
		return "login";
	}
	
	
	

}
