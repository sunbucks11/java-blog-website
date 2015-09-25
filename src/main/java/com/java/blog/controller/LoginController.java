package com.java.blog.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.blog.entity.User;
import com.java.blog.repository.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	boolean is2faSetup = false;
	boolean is2faVerified = false;

	// @RequestMapping("/login")
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ModelAndView modelAndView = new ModelAndView("barcode");
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");

		// if (username != null && password != null)
		// {
		if (checkCredentials(username, password)) {

			if (!is2faSetup) {
				// user want to set up 2fa
				final GoogleAuthenticatorKey key = googleAuthenticator
						.createCredentials();
				final String secret = key.getKey();
				request.getSession().setAttribute("secretKey", secret);
				String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
						+ username + "?secret=" + secret;

				modelAndView.getModelMap().put("secretKey", secret);
				modelAndView.getModelMap().put("barCodeUrl", otpAuthURL);
				modelAndView.getModelMap().put("initAuth", true);
				is2faSetup = true;

			} else {
				if(is2faVerified)
				{
					return new ModelAndView("redirect:/index.html");
				}
				else{
					// forward to verify code
					is2faVerified = true; 
					return new ModelAndView("redirect:/verification.html");
				}

			}

		} else {

			// System.out.println("Unknown user, please try again");
			return new ModelAndView("redirect:/login.html");
		}

		return modelAndView;
	}

	private boolean checkCredentials(String username, String password) {
		
		if (userRepository.findByName(username) != null) 
		{
			User user = userRepository.findByName(username);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			if ((encoder.matches(password, user.getPassword()))
					&& (user.getName().equals(username.trim()))) {
				return true;
			}
		}
		return false;
	}
}
