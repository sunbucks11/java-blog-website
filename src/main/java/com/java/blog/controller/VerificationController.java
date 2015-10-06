package com.java.blog.controller;

import home.test.googauth.GoogleAuthenticator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.blog.model.TwoFactorAuthForm;
import com.java.blog.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
@RequestMapping("/VerificationController")
public class VerificationController {

	@Autowired
	private UserService userService;
	
	// @RequestMapping("/verification")
	@RequestMapping(method = RequestMethod.GET)
	public String verification() {
		return "verification";
	}

	@RequestMapping(method = RequestMethod.POST)
	public void handleTwoFactorAuthInitialisation(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("formBean") TwoFactorAuthForm twoFactorAuthForm,
			BindingResult bindingResult) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		SecurityContextImpl sci = (SecurityContextImpl) session
				.getAttribute("SPRING_SECURITY_CONTEXT");

		if (sci != null) {

			String codestr = request.getParameter("code");
			long code = Long.parseLong(codestr);
			long t = System.currentTimeMillis();

			GoogleAuthenticator ga = new GoogleAuthenticator();
			ga.setWindowSize(5); // should give 5 * 30 seconds of grace

			// Get the secret key from the session , you will get it from the
			// db.
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

			boolean result = ga.check_code(savedSecret, code, t);

			if (result && username != null) {
				// pw.write("<html><body><h1>Code Verification Successful</h1></body></html>");
				
				request.getSession().setAttribute("isVerified", true);
				request.getSession().setAttribute("isAuthenticated", true);
				
				userService.findOne(username).setAuthenticated(true);
				userService.findOne(username).setVerified(true);
				userService.findOne(username).setVerifiedError(false);
				TwoFactorAuthController.isVerificationRequired = false; 
				request.getRequestDispatcher("/IndexController").forward(request, response);
				// return "index";
			} else {
				// pw.write("<html><body><h1>Code Verification Unsuccessful please contact the Administrator</h1></body></html>");
				//request.getSession().setAttribute("isError", true);
					//request.getSession().setAttribute("isVerified", false);
				
				request.getSession().setAttribute("isVerifiedError", false);
				
				//TwoFactorAuthController.isVerificationRequired = true; 
				
				userService.findOne(username).setVerified(false);
				userService.findOne(username).setVerifiedError(true);
				request.getRequestDispatcher("/ErrorController").forward(request, response);
			}
		}
	}
}
