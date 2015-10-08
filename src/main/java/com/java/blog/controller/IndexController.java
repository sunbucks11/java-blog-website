package com.java.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.blog.service.ItemService;

//@Controller
//@RequestMapping("/IndexController")
//public class IndexController {
//
//	@Autowired
//	private ItemService itemService;
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public String index(Model model) {
//		model.addAttribute("items", itemService.getItems());
//		return "index";
//	}
//	
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public String indexPost(Model model) {
//		model.addAttribute("items", itemService.getItems());
//		return "index";
//	}
//}


@Controller
public class IndexController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("items", itemService.getItems());
		return "index";
	}
}