package com.java.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ResetController")
public class ResetController {
	
	@RequestMapping(method = RequestMethod.POST)
	public String indexPost(Model model, HttpServletRequest request, HttpServletResponse response) {
	  TwoFactorAuthController.isResetTwoFactorAuth = true; 
	  TwoFactorAuthController.isVerificationRequired = true; 
	  TwoFactorAuthController.TWO_FACTOR_AUTHENTICATION_INT = false; 	
	  
	  String backToLogin = request.getParameter("back-login");
	  
		if (backToLogin != null)
		{
			return "redirect:/login.html";
		}
	  
		return "reset";
	}	
}
