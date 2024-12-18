package com.grade.gradeweb.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grade.gradeweb.models.AppUser;
import com.grade.gradeweb.models.RegisterDto;
import com.grade.gradeweb.services.AppUserService;

public class AccountControllerTest {

  

    @Mock
    private AppUserService appUserService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_EmailAlreadyUsed() {

    	RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("existing@example.com");
        registerDto.setPassword("password123");
        registerDto.setConfirmPassword("password123");
        registerDto.setFirstName("Joe");
        registerDto.setLastName("Jonas");
        registerDto.setRole("STUDENT");

        AppUser existingUser = new AppUser();
        when(appUserService.findByEmail(registerDto.getEmail())).thenReturn(existingUser);

        String viewName = accountController.register(registerDto, bindingResult, model, redirectAttributes);

        assertEquals("register", viewName);
        verify(bindingResult).addError(any());
        verify(model).addAttribute("success", false);
        verify(model).addAttribute("message", "Email address is already used.");
    }

    @Test
    public void testRegister_PasswordsDoNotMatch() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("newuser@example.com");
        registerDto.setPassword("password123");
        registerDto.setConfirmPassword("differentpass123");
        registerDto.setFirstName("Mary");
        registerDto.setLastName("Jane");
        registerDto.setRole("STUDENT");

        String viewName = accountController.register(registerDto, bindingResult, model, redirectAttributes);

        assertEquals("register", viewName);
        verify(bindingResult).addError(any());
        verify(model).addAttribute("success", false);
        verify(model).addAttribute("message", "Password and Confirm Password do not match.");
    }
}
