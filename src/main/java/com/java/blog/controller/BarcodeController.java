package com.java.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BarcodeController {

	  @RequestMapping("/barcode")
	  public String barcode() {
	    return "barcode";
	  }
}
