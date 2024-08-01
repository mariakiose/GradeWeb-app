package com.grade.gradeweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
	
	@GetMapping("/index")
	public String giveHome() {
		return "index";
	}
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	
}
