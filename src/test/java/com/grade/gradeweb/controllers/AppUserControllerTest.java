package com.grade.gradeweb.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.grade.gradeweb.models.AppUser;
import com.grade.gradeweb.services.AppUserService;

@WebMvcTest(AppUserController.class)
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService userService;

    private AppUser mockUser;

    @BeforeEach
    public void setup() {
        mockUser = new AppUser();
        mockUser.setEmail("test@example.com");
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        mockUser.setPhone("1234567890");
        mockUser.setAddress("123 Street");
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testStudentsProfile() throws Exception {
        when(userService.findByEmail("test@example.com")).thenReturn(mockUser);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attribute("user", mockUser));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "USER")
    public void testUpdateProfile() throws Exception {
        AppUser updatedUser = new AppUser();
        updatedUser.setEmail("test@example.com");
        updatedUser.setFirstName("UpdatedName");
        updatedUser.setLastName("UpdatedLastName");
        updatedUser.setPhone("0987654321");
        updatedUser.setAddress("321 Street");

        when(userService.updateUserProfile(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(updatedUser);

        mockMvc.perform(post("/updateProfile")
                .param("email", "test@example.com")
                .param("firstName", "UpdatedName")
                .param("lastName", "UpdatedLastName")
                .param("phone", "0987654321")
                .param("address", "321 Street")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(userService, times(1)).updateUserProfile("test@example.com", "UpdatedName", "UpdatedLastName", "0987654321", "321 Street");
    }
}
