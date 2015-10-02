package com.java.blog.controller;

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
	public String indexPost(Model model) {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
		return "error";
	}

}
