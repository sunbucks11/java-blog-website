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

//@Controller
//@RequestMapping("/ErrorController")
//public class ErrorController {
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private UserRepository userRepository; 
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public String index(Model model) {
//		return "error";
//	}
//	
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public String indexPost(Model model, HttpServletRequest request, HttpServletResponse response) {
//		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
//		//request.getSession().setAttribute("isVerified", false);
//		
//		
//		String username = null;
//		HttpSession session = request.getSession(true);
//
//		SecurityContextImpl sci = (SecurityContextImpl) session
//				.getAttribute("SPRING_SECURITY_CONTEXT");
//			UserDetails cud = (UserDetails) sci.getAuthentication()
//					.getPrincipal();
//			username = cud.getUsername();
//	
//	
//		 //userService.findOne(username).setVerifiedError(true);
//		 //userService.findOne(username).setResetTwoFactorAuth(true);
//		 
//		 
//		 //User user = userRepository.findByName(username);	 
//		// user.setResetTwoFactorAuth(true);	 
//		// System.out.println("DATABASE isResetTwoFactorAuth: From ERROR Page " +  user.isResetTwoFactorAuth());
//
//		//userService.findOne(username).setVerifiedError(true);
//			
//			// TwoFactorAuthController.isResetTwoFactorAuth = true; 
//			
//		return "error";
//	}
//	
//	
//	@RequestMapping(method = RequestMethod.POST, value="/java-blog-website/ErrorController/Reset")
//	public String Reset(Model model, HttpServletRequest request, HttpServletResponse response) {
//		TwoFactorAuthController.isResetTwoFactorAuth = true; 
//		
//		return "login";
//		
//	}	
//
//}



@Controller
public class ErrorController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository; 
	
	@RequestMapping("/error")
	public String index(Model model) {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
		return "error";
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public String indexPost(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("error", "Wrong Verfication Number, Please Contact the Administrator.");
		//request.getSession().setAttribute("isVerified", false);
		
		
		String username = null;
		HttpSession session = request.getSession(true);

		SecurityContextImpl sci = (SecurityContextImpl) session
				.getAttribute("SPRING_SECURITY_CONTEXT");
			UserDetails cud = (UserDetails) sci.getAuthentication()
					.getPrincipal();
			username = cud.getUsername();
				
		return "error";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/java-blog-website/ErrorController/Reset")
	public String Reset(Model model, HttpServletRequest request, HttpServletResponse response) {
		TwoFactorAuthController.isResetTwoFactorAuth = true; 
		
		return "login";
		
	}	

}
