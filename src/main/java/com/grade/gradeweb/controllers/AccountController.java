package com.grade.gradeweb.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grade.gradeweb.models.AppUser;
import com.grade.gradeweb.models.RegisterDto;
import com.grade.gradeweb.services.AppUserService;

import jakarta.validation.Valid;

@Controller
public class AccountController {

    
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDto") RegisterDto registerDto,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "There are errors in the form.");
            return "register";
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(new FieldError("registerDto", "confirmPassword",
                    "Password and Confirm Password do not match"));
            model.addAttribute("success", false);
            model.addAttribute("message", "Password and Confirm Password do not match.");
            return "register";
        }

        AppUser existingUser = appUserService.findByEmail(registerDto.getEmail());
        if (existingUser != null) {
            result.addError(new FieldError("registerDto", "email",
                    "Email address is already used"));
            model.addAttribute("success", false);
            model.addAttribute("message", "Email address is already used.");
            return "register";
        }

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhone(registerDto.getPhone());
            newUser.setAddress(registerDto.getAddress());
            newUser.setPassword(encoder.encode(registerDto.getPassword()));

            // Set role based on user selection
            String role = registerDto.getRole();
            if ("SECRETARY".equals(role) || "STUDENT".equals(role)) {
                newUser.setRole(role);
            } else {
                newUser.setRole("STUDENT"); // Default role
            }
            newUser.setCreateAt(new Date()); // Assuming you have a createdAt field


            appUserService.createUser(newUser, role);

            //appUserRepository.save(newUser);

            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Account created successfully!");
           
            return "redirect:/register"; // Redirect to the registration page to display the success message
        } catch (Exception ex) {
            result.addError(new FieldError("registerDto", "firstName", ex.getMessage()));
            model.addAttribute("success", false);
            model.addAttribute("message", "An error occurred while creating the account.");
            return "register";
        }
    }

   
}