package com.java.blog.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BarcodeController {

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


	 @RequestMapping("/barcode")
	    @ResponseBody
	    public HttpEntity<byte[]> barcode() throws IOException {
	    	byte[] image = null; 
	    	HttpHeaders headers = new HttpHeaders();
	        
	        
	    	   try{
	   			
	    			BufferedImage originalImage = ImageIO.read(new File("/Users/semiribrahim/Documents/Work/DHP/Spring_2_Planning/confdemo/images/demo/barry.jpeg"));
	    					
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

	

	/*

	    @RequestMapping("/barcode")
	    public ModelAndView barcode() {

	    	ModelAndView mv = new ModelAndView("/Scene/scene");
	    	
	    	return mv; 
	    	   
	    }
	*/
	    
}
