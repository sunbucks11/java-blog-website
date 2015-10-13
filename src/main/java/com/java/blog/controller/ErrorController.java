package com.java.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	@RequestMapping("/error")
	public String index(Model model) {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
		return "error";
	}
}
