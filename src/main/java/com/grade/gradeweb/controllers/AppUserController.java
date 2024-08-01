package com.grade.gradeweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grade.gradeweb.services.AppUserService;
import com.grade.gradeweb.models.AppUser;

@Controller
public class AppUserController {
	 @Autowired
	 private AppUserService userService;
   
	 @GetMapping("/profile")
	  public String studentsProfile(Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        AppUser user = userService.getUser(userDetails.getUsername());
	        model.addAttribute("user", user);
	        return "profile";
	    }	
	 
	 @PostMapping("/updateProfile")
	    public String updateProfile(@RequestParam("email") String email,
	                                @RequestParam("firstName") String firstName,
	                                @RequestParam("lastName") String lastName,
	                                @RequestParam("phone") String phone,
	                                @RequestParam("address") String address,
	                                Model model) {
	        AppUser updatedUser = userService.updateUserProfile(email, firstName, lastName, phone, address);
	        model.addAttribute("user", updatedUser);
	        return "redirect:/profile";
	 }  
    
}
