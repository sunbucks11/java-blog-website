package com.java.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	@RequestMapping("/error")
	public String index(Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
		request.logout();
		return "error";
	}
}
