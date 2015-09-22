package com.java.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AuthenticationController {
	@RequestMapping("/authenticator")
	public String authenticator() {
		return "authenticator";
	}
}
