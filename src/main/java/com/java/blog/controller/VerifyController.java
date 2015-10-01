package com.java.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.java.blog.entity.User;
import com.java.blog.repository.UserRepository;

import home.test.googauth.GoogleAuthenticator;

/**
 * Servlet implementation class VerifyController
 */
@WebServlet("/VerifyController")
public class VerifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Autowired
	private UserRepository userRepository;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String codestr = request.getParameter("code");
		
		long code =	Long.parseLong(codestr);
		
		long t = System.currentTimeMillis();
		
		GoogleAuthenticator ga = new GoogleAuthenticator();
		
		ga.setWindowSize(5);  //should give 5 * 30 seconds of grace
		
		//Get the secret key from the session , you will get it from the db.
		String savedSecret = (String)request.getSession().getAttribute("secretKey");
		
		boolean result = ga.check_code(savedSecret, code, t);
		
		PrintWriter pw = response.getWriter();
		
		if (result) {
			//pw.write("<html><body><h1>Code Verification Successful</h1></body></html>");	
		
			 request.getSession().setAttribute( "isVerified", true );
			 request.getSession().setAttribute( "isAuthenticated", true );
			 response.sendRedirect("/java-blog-website/index.jsp");		
		} else {
			//pw.write("<html><body><h1>Code Verification Unsuccessful</h1></body></html>");
			response.sendRedirect("/java-blog-website/login.html");
		}
	}

}
