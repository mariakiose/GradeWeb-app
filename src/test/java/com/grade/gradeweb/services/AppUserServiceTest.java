package com.grade.gradeweb.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.grade.gradeweb.models.AppUser;
import com.grade.gradeweb.repositories.AppUserRepository;

public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserService appUserService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateUserProfile_UserExists() {
        // Test data
        AppUser appUser = new AppUser();
        appUser.setEmail("user@example.com");

        when(appUserRepository.findByEmail("user@example.com")).thenReturn(appUser);

        // Execute the method
        AppUser updatedUser = appUserService.updateUserProfile("user@example.com", "NewFirstName", "NewLastName", "1234567890", "New Address");

        // Verify the results
        assertNotNull(updatedUser);
        assertEquals("NewFirstName", updatedUser.getFirstName());
        assertEquals("NewLastName", updatedUser.getLastName());
        assertEquals("1234567890", updatedUser.getPhone());
        assertEquals("New Address", updatedUser.getAddress());

        verify(appUserRepository).save(updatedUser);
    }

    @Test
    public void testUpdateUserProfile_UserDoesNotExist() {
        // Simulate user not found
        when(appUserRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Execute the method
        AppUser updatedUser = appUserService.updateUserProfile("nonexistent@example.com", "FirstName", "LastName", "1234567890", "Address");

        // Verify that the user was not updated
        assertNull(updatedUser);
        verify(appUserRepository, never()).save(any(AppUser.class));
    }
}
