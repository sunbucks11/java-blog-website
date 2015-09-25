package com.java.blog.controller;

import home.test.googauth.GoogleAuthenticator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
/**
 * Servlet implementation class SetUpController
 */
@WebServlet("/SetUpController")
public class SetUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		
		//Get the secret key
		String secretKey = GoogleAuthenticator.generateSecretKey();
		
		// set the session key to session scope 
		// then we can recall it. (just for the demonstration purpose)
		request.getSession().setAttribute( "secretKey", secretKey );
		
		// Here note that we must follow this url pattern 
		// otherwise Google Authenticator app won't decode the info.
		// more in info @ https://code.google.com/p/google-authenticator/wiki/KeyUriFormat
		String s = "otpauth://totp/"+username+"?secret="+secretKey;
		
		// Get the Qr Code png as a OutPutStream
		ByteArrayOutputStream outs = QRCode.from(s).to(ImageType.PNG).stream();

		response.setContentType("image/png");
		response.setContentLength(outs.size());
		
		OutputStream outStream = response.getOutputStream();
		
		outStream.write(outs.toByteArray());
		
		outStream.flush();
		outStream.close();
		
	}

}
