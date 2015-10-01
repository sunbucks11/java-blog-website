package com.java.blog.controller;

//import home.test.googauth.GoogleAuthenticator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
@RequestMapping("/barcode")
public class BarcodeController {

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView barcode() {	
    	 ModelAndView modelAndView = new ModelAndView( "barcode" );
         GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator( );

         final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials( );
         final String secret = key.getKey( ); 
         String username = "Semir";	
     	 String otpAuthURL =  "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/" +username+"?secret="+secret;
        //String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth%3A%2F%2Ftotp%2Fjanet%3Ajanet%40teamnetsol.com%3Fsecret%3DGM5Z4FTOE5CDQ3F4%26issuer%3Djanet";

         modelAndView.getModelMap( ).put( "secretKey", secret );
         modelAndView.getModelMap( ).put( "barCodeUrl", otpAuthURL );
         modelAndView.getModelMap( ).put( "initAuth", true ); 
         return modelAndView;	
    }   		    
}
