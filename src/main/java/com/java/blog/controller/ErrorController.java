package com.java.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ErrorController")
public class ErrorController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		return "error";
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String indexPost(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
		//request.getSession().setAttribute("isVerified", false);
		return "error";
	}

}
