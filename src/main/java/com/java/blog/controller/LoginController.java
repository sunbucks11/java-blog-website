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
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	public static final String TWO_FACTOR_AUTHENTICATION_SUCCESS = "TWO_FACTOR_AUTHENTICATION";

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

		if (checkCredentials(username, password)) 
		{
			request.getSession().setAttribute("isAuthenticated", true);

			if (request.getSession().getAttribute("isAdmin") == null
					&& request.getSession().getAttribute("isVerified") == null) {
				// user want to set up 2fa
				final GoogleAuthenticatorKey key = googleAuthenticator
						.createCredentials();
				final String secret = key.getKey();
				request.getSession().setAttribute("secretKey", secret);
				String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
						+ username + "?secret=" + secret;

				// modelAndView.getModelMap().put("initAuth", true);
				// modelAndView.getModelMap().put("secretKey", secret);
				modelAndView.getModelMap().put("barCodeUrl", otpAuthURL);

				request.getSession().setAttribute("isAdmin", true);
				// request.getSession().setAttribute( "username",
				// request.getParameter("j_username") );
				return modelAndView;
			}

			if (request.getSession().getAttribute("isVerified") == null) {
				return new ModelAndView("redirect:/verification.html");
			} else {
				return new ModelAndView("redirect:/index.html");
			}
		} else {
			return new ModelAndView("redirect:/login.html");
		}
	}

	private boolean checkCredentials(String username, String password) {

		if (userRepository.findByName(username) != null) {
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
