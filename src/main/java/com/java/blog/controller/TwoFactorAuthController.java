package com.java.blog.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.blog.model.TwoFactorAuthForm;
import com.java.blog.service.UserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
@RequestMapping("/TwoFactorAuthController")
public class TwoFactorAuthController {

	// private static Logger log = LoggerFactory.getLogger(
	// TwoFactorAuthController.class );

	public static final String TWO_FACTOR_AUTHENTICATION_SUCCESS = "TWO_FACTOR_AUTHENTICATION";

	public static boolean isResetTwoFactorAuth = false;
	public static boolean isVerificationRequired = true;

	public static boolean TWO_FACTOR_AUTHENTICATION_INT = false;

	public static GoogleAuthenticatorKey SecretKey;

	public static boolean TWO_FACTOR_VERIFIED = false;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleGetTwoFactorAuth(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		ModelAndView modelAndView = new ModelAndView("barcode");

		HttpSession session = request.getSession(true);
		SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username = null;

		if (sci != null) {
			UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
			username = cud.getUsername();
			userService.findOne(username);
			TwoFactorAuthForm twoFactorAuthForm = new TwoFactorAuthForm(userService.findOne(username));

			System.out.println("Current User: " + userService.findOne(username).getName());

			try {

				if (userService.findOne(username).getTwoFactorAuthInitialised() == null
						&& !TWO_FACTOR_AUTHENTICATION_INT) {
					GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

					final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
					final String secret = key.getKey();

					String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
							+ userService.findOne(username).getName() + "?secret=" + secret;

					modelAndView.getModelMap().put("secretKey", secret);
					modelAndView.getModelMap().put("barCodeUrl", otpAuthURL);
					modelAndView.getModelMap().put("initAuth", true);

					// NOT SURE WHICH ERROR THIS REPRESENT
					request.getSession().setAttribute("isError", false);

					userService.findOne(username).setSecretKey(secret);
					this.SecretKey = key;
					TWO_FACTOR_AUTHENTICATION_INT = true;
					TwoFactorAuthController.isResetTwoFactorAuth = false;
				} else {
					request.getRequestDispatcher("/verification.html").forward(request, response);

				}

				modelAndView.getModelMap().put("formBean", twoFactorAuthForm);
			} catch (Exception e) {
				e.printStackTrace();
				modelAndView = redirectWithMessage("/", "Something went wrong while authenticating user");
			}
		}

		return modelAndView;
	}

	private ModelAndView redirectWithMessage(String redirectUrl, String errorMessage) {
		ModelAndView mav = new ModelAndView("redirect:" + redirectUrl);
		mav.getModelMap().put("message", errorMessage);
		return mav;
	}

}