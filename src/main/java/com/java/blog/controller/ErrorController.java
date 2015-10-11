package com.java.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
	
	@RequestMapping("/error")
	public String index(Model model) {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
		return "error";
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public String indexPost(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");				
		return "error";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/java-blog-website/ErrorController/Reset")
	public String Reset(Model model, HttpServletRequest request, HttpServletResponse response) {
		TwoFactorAuthController.isResetTwoFactorAuth = true; 
		return "login";	
	}	

}
