package com.java.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VerificationController {
	@RequestMapping("/verification")
	public String verification() {
		return "verification";
	}

}
