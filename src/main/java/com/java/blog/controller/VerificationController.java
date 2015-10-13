package com.java.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.blog.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
public class VerificationController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/verification")
	public String verification() {
		return "verification";
	}

	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	public String handleVerification(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{
		
		String exitVerificationBtn = request.getParameter("exit-verification");
		//String saveVerificationBtn = request.getParameter("save-verification");
		String verificationCodeTxtBox = request.getParameter("code");
				
		HttpSession session = request.getSession(true);
		SecurityContextImpl sci = (SecurityContextImpl) session
				.getAttribute("SPRING_SECURITY_CONTEXT");
	
		if(verificationCodeTxtBox == null || verificationCodeTxtBox.trim().length() == 0)
		{
			return "redirect:/logout";
		}
		
		if (exitVerificationBtn != null)
		{
			return "redirect:/logout";
		}

		if (sci != null) {

			String codestr = request.getParameter("code");
			//long code = Long.parseLong(codestr);
			Integer code = Integer.parseInt(codestr);
			long t = System.currentTimeMillis();

			GoogleAuthenticator ga = new GoogleAuthenticator();
			//ga.setWindowSize(5); // should give 5 * 30 seconds of grace

			// Get the secret key from the session , you will get it from the db.
			String savedSecret = (String) request.getSession().getAttribute("secretKey");
			String username = null;

			if (savedSecret == null) {

				UserDetails cud = (UserDetails) sci.getAuthentication()
						.getPrincipal();
				username = cud.getUsername();
				userService.findOne(username).getSecretKey();

				GoogleAuthenticatorKey key = TwoFactorAuthController.SecretKey;

				System.out.println("key is: " + key.getKey());
				savedSecret = key.getKey();
			}

			//boolean result = ga.check_code(savedSecret, code, t);
			boolean result = ga.authorize(savedSecret, code);
			

			if (result && username != null) 
			{			
				request.getSession().setAttribute("isVerified", true);
				request.getSession().setAttribute("isAuthenticated", true);
				
				userService.findOne(username).setAuthenticated(true);
				userService.findOne(username).setVerified(true);
				userService.findOne(username).setVerifiedError(false);
				TwoFactorAuthController.isVerificationRequired = false; 
				return "redirect:/index.html";
			} else {
				
				request.getSession().setAttribute("isVerifiedError", false);
				userService.findOne(username).setVerified(false);
				userService.findOne(username).setVerifiedError(true);
			}
		}
		
		return "redirect:/error.html";
	}
}