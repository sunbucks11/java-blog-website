package com.java.blog.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthController
 */
@WebServlet("/AuthController")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		boolean is2faSetup  = true;
		
		if(request.getParameter("setup") == null)
		{
			is2faSetup = false;  
		} 
		
		if (username != null && password != null) {
			
			if(is2faSetup){
			 // user want to set up 2fa 
				request.setAttribute("username", username);
				request.getRequestDispatcher("/SetUpController").forward(request,
						response);
			}else{
			// forward to verify code				
				request.setAttribute("username", username);
				response.sendRedirect("/java-blog-website/verification.html");
			}
							
		} else {
			
			request.setAttribute("error", "Unknown user, please try again");
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		}
	}

}
