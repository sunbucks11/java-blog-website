package com.java.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	public static final String TWO_FACTOR_AUTHENTICATION_SUCCESS = "TWO_FACTOR_AUTHENTICATION";
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
