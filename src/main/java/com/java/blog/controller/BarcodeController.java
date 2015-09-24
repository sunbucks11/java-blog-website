package com.java.blog.controller;

//import home.test.googauth.GoogleAuthenticator;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

@Controller
public class BarcodeController {

	@Autowired
	ServletContext servletContext;
	
/*
	  @RequestMapping("/barcode")
		  public String barcode(Model model){
		  
		  String user = "test";
		  String host = "localhost";
		  String secret = "test";
	      String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
        
	      model.addAttribute("qrBarcode", format);  
	      return "barcode";
	  }
*/	


/*	
	 @RequestMapping("/barcode")
	    @ResponseBody
	    public HttpEntity<byte[]> barcode() throws IOException {
	    	byte[] image = null; 
	    	HttpHeaders headers = new HttpHeaders();
	        
	        
	    	   try{
	    			BufferedImage originalImage = ImageIO.read(new File(servletContext.getRealPath("/WEB-INF/img/barry.jpeg")));
	    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    			ImageIO.write( originalImage, "jpeg", baos );
	    			baos.flush();
	    			image = baos.toByteArray();
	    			baos.close();
	    	       
	    	        headers.setContentType(MediaType.IMAGE_JPEG); //or what ever type it is
	    	        headers.setContentLength(image.length);
	    	        		
	    			}catch(IOException e){
	    				System.out.println(e.getMessage());
	    			}		       
	       
	    	   return new HttpEntity<byte[]>(image, headers);
	    }
*/
	

	
/*
	    @RequestMapping("/barcode")
	    public ModelAndView barcode() {
	    	
	    	byte[] image = null; 
	    	HttpHeaders headers = new HttpHeaders();
	    	BufferedImage originalImage = null; 
	    	
	    	   try{
	    			originalImage = ImageIO.read(new File(servletContext.getRealPath("/WEB-INF/img/barry.jpeg")));
	    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    			ImageIO.write( originalImage, "jpeg", baos );
	    			baos.flush();
	    			image = baos.toByteArray();
	    			baos.close();
	    	       
	    	        headers.setContentType(MediaType.IMAGE_JPEG); //or what ever type it is
	    	        headers.setContentLength(image.length);
	    	        		
	    			}catch(IOException e){
	    				System.out.println(e.getMessage());
	    			}
	    	   
	    	   //String 	filepath = servletContext.getRealPath("/WEB-INF/img/barry.jpeg");

	    	return new ModelAndView("barcode","QRbarcode", "/WEB-INF/img/barry.jpeg");	    	   
	    }
*/
	
    @RequestMapping("/barcode")
    public ModelAndView barcode() {	
    	 ModelAndView modelAndView = new ModelAndView( "barcode" );
         GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator( );

         final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials( );
         final String secret = key.getKey( );
         
         String username = "tom";
        
     //   String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL( "test", "test@test.com",key );
         
     	//String otpAuthURL = "otpauth://totp/"+"test"+"?secret="+secret;	
     	 String otpAuthURL =  "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/" +username+"?secret="+secret;
        //String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth%3A%2F%2Ftotp%2Fjanet%3Ajanet%40teamnetsol.com%3Fsecret%3DGM5Z4FTOE5CDQ3F4%26issuer%3Djanet";
     	
     	
     	System.out.println("Secret: " + secret);
     	System.out.println("barCodeUrl: " + otpAuthURL);
     	System.out.println("initAuth: " + true);
     	

         modelAndView.getModelMap( ).put( "secretKey", secret );
         modelAndView.getModelMap( ).put( "barCodeUrl", otpAuthURL );
         modelAndView.getModelMap( ).put( "initAuth", true );
    	
    	
        //return new ModelAndView("barcode","QRbarcode", modelAndView);
        
        return modelAndView;
    	
    	
    	
    	
    }
    	
	    
}
